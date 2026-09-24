# RutaIA - Backend (Spring Boot)

Backend REST para el proyecto RutaIA: registro de estudiantes, catálogo de cursos,
consultas en lenguaje natural, orquestación del flujo RAG (n8n + Qdrant + OpenRouter),
historial, calificaciones y estadísticas.

## Requisitos previos
- Java 17
- MySQL corriendo con la base `RutaIA` ya creada (tu script SQL)
- (Para el flujo completo) n8n con un workflow publicado como Webhook

## Cómo ejecutar

1. Genera el wrapper de Gradle una sola vez (necesitas Gradle instalado o el IDE lo hace por ti):
   ```
   gradle wrapper
   ```
2. Configura las variables de entorno (o edita `src/main/resources/application.yml` directamente):
   ```
   DB_HOST=localhost
   DB_PORT=3306
   DB_USERNAME=root
   DB_PASSWORD=tu_password
   N8N_WEBHOOK_URL=http://localhost:5678/webhook/rutaia-consulta
   ```
3. Ejecuta la aplicación:
   ```
   ./gradlew bootRun
   ```
4. Swagger UI:
   ```
   http://localhost:8080/swagger-ui.html
   ```

## Contrato con n8n

Spring Boot llama por POST a `n8n.webhook-url` con este body:

```json
{
  "consultaId": 10,
  "pregunta": "Quiero aprender a crear páginas web",
  "nivelExperiencia": "PRINCIPIANTE",
  "areaInteres": "Desarrollo web"
}
```

Y espera como respuesta:
```json
{
  "estado": "RESPONDIDA",
  "respuesta": "Texto generado por el modelo, basado solo en el contexto recuperado...",
  "fuentes": [
    { "cursoId": 3, "similitud": 0.8421 },
    { "cursoId": 7, "similitud": 0.7912 }
  ]
}
```

- `estado` debe ser uno de: `RESPONDIDA`, `SIN_RESULTADOS`, `ERROR`.
- Si `estado` es `SIN_RESULTADOS`, `fuentes` puede ir vacío.
- El umbral de similitud se aplica **dentro de n8n**, antes de construir el
  contexto que se envía al modelo (property `rag.umbral-similitud`, sugerida como
  referencia para tu workflow).

## Estructura de paquetes

```
com.rutaia.backend
 ├── entity        -> Entidades JPA (mapeadas 1:1 a tu script SQL)
 ├── repository     -> Spring Data JPA
 ├── dto            -> Request/Response DTOs (nunca se exponen las entidades directamente)
 ├── service        -> Interfaces + implementación con la lógica de negocio
 ├── controller     -> Endpoints REST
 ├── exception      -> Excepciones propias + manejador global (@RestControllerAdvice)
 └── config         -> CORS, propiedades de n8n, RestClient
```

## Endpoints principales

| Método | Endpoint                               |
|--------|----------------------------------------|
| POST   | /api/estudiantes                       | 
| GET    | /api/estudiantes                       | 
| GET    | /api/estudiantes/{id}                  | 
| GET    | /api/estudiantes/{id}/historial        | 
| POST   | /api/cursos                            | 
| PUT    | /api/cursos/{id}                       | 
| PATCH  | /api/cursos/{id}/desactivar            | 
| GET    | /api/cursos?categoria=&nivel=          | 
| POST   | /api/consultas                         | 
| GET    | /api/consultas/{id}                    | 
| POST   | /api/calificaciones                    | 
| GET    | /api/estadisticas                      | 

---

# Base de datos, Qdrant y automatizaciones (n8n)

Esta sección documenta la base de datos relacional (MySQL), la base de datos
vectorial (Qdrant) y los flujos de automatización (n8n) del proyecto.

## Base de datos relacional (MySQL)

### Script SQL

El script completo de creación de la base de datos está en [`\RutaIA\MySQL\Schema.sql`](.\RutaIA\MySQL\Schema.sql).

Además encuentras un scrip para insersion de cursos y estudiantes (como ejemplo de prueba)
[`\RutaIA\MySQL\Data.sql`](.\RutaIA\MySQL\Data.sql).

### Tablas

| Tabla | Descripción |
|---|---|
| `estudiante` | Nombre, correo (único), nivel de experiencia, área de interés |
| `curso` | Nombre, descripción, categoría, nivel, duración, estado (activo/inactivo), `fecha_actualizacion` (usada por n8n para sincronización incremental) |
| `consulta` | Pregunta del estudiante, fecha, estado (`PENDIENTE`, `RESPONDIDA`, `SIN_RESULTADOS`, `ERROR`) |
| `recomendacion` | Respuesta generada, fecha, estado final, ligada a una `consulta` |
| `fuente` | Relación N:M entre `recomendacion` y `curso`, con la similitud (score) de cada fuente |
| `calificacion` | Puntuación (1-5) y comentario opcional por recomendación (una sola por recomendación) |
| `sincronizacion_qdrant` | ultima_sincronizacion (usada por n8n para llevar un registro de ultima actualizacion realizada) |

### Cómo ejecutar el script

```bash
mysql -u root -p < RutaIA\MySQL\Data.sq
```

Esto crea la base de datos `RutaIA` y todas las tablas con sus relaciones,
restricciones e índices.

## Base de datos vectorial (Qdrant)

### Configuración de la colección

| Parámetro | Valor |
|---|---|
| Nombre de la colección | `cursos-RutaIA` |
| Dimensión del vector | `1536` |
| Métrica de distancia | `Cosine` |
| Modelo de embeddings | `openai/text-embedding-3-small` (vía OpenRouter) |

### Estructura de cada punto

```json
{
  "id": 5,
  "vector": [/* 1536 números */],
  "payload": {
    "curso_id": 5,
    "nombre": "...",
    "descripcion": "...",
    "categoria": "...",
    "nivel": "...",
    "duracion": 40,
    "estado": "ACTIVO"
  }
}
```

El `id` del punto en Qdrant es siempre el mismo `id` del curso en MySQL, lo que
permite hacer *upsert* sin generar duplicados al re-sincronizar.

### Umbral de similitud

`[0.40(`UMBRAL_MINIMO`)
usado en el nodo "Filtrar Umbral" del flujo de consulta, con base en pruebas
reales sobre las consultas de la sección 10 del enunciado]`

### Acceder al dashboard de Qdrant

```
http://localhost:6333/dashboard
```

## Automatizaciones (n8n)

Hay dos workflows de n8n en [`\RutaIA\Automatizacion`](.\RutaIA\Automatizacion):

### 1. `Ruta-IA DATOS` — Carga y sincronización de cursos en Qdrant

Obtiene los cursos activos de MySQL, genera sus embeddings vía OpenRouter y
los inserta/actualiza (*upsert*) en Qdrant.

- **Trigger:** manual (botón "Execute workflow" dentro de n8n).
- **Sincronización incremental:** solo procesa cursos cuya `fecha_actualizacion`
  sea posterior a la última ejecución exitosa (guardada internamente en el
  workflow). Si no hay cursos nuevos o modificados, el flujo termina sin
  llamar a OpenRouter ni a Qdrant.
- **Resumen del flujo:**
  ```
  Manual Trigger → Obtener última sincronización → SELECT cursos modificados
  → (¿hay resultados?) → Preparar texto → Generar Embeddings (OpenRouter)
  → Armar punto → Agrupar → Crear colección (si no existe) → Guardar en Qdrant
  → Actualizar última sincronización
  ```

**Cómo ejecutarlo la primera vez:** correrlo manualmente una vez con la base
de datos ya poblada (mínimo 20 cursos activos) para la carga inicial completa.
Después, puede volver a ejecutarse cuantas veces se necesite — solo procesará
los cursos que hayan cambiado.

### 2. `Ruta-IA Consulta` — Flujo RAG en tiempo real

Recibe la pregunta del estudiante desde Spring Boot, busca los cursos más
relevantes en Qdrant y genera una respuesta con el modelo de lenguaje.

- **Trigger:** Webhook (`POST`), expuesto en la ruta `/webhook/buscar_curso_rutaIA`.
- **Contrato de entrada** (enviado por Spring Boot):
  ```json
  {
    "idConsulta": 1,
    "Pregunta": "pregunta del estudiante",
    "Nivel": "nivel del estudiante",
    "areaInteres": "área de interés"
  }
  ```
- **Contrato de salida:**
  ```json
  {
    "estado": "RESPONDIDA",
    "respuesta": "Texto generado por el modelo...",
    "fuentes": [
      { "cursoId": 3, "similitud": 0.8421 },
      { "cursoId": 7, "similitud": 0.7912 }
    ]
  }
  ```
- **Resumen del flujo:**
  ```
  Webhook → Preparar pregunta → Generar embedding de la pregunta (OpenRouter)
  → Buscar en Qdrant (solo cursos activos) → Filtrar por umbral de similitud
  → (¿hay resultados?)
      SÍ → Generar respuesta con el modelo (OpenRouter chat) → Formatear
      NO → Responder "sin resultados"
  → Responder al Frontend (vía Spring Boot)
  ```
- **Manejo de errores:** cada paso crítico (embedding, búsqueda en Qdrant,
  generación de respuesta) tiene una rama de error propia que responde con
  `estado: "ERROR"` sin detener el resto del sistema.

### Importar los workflows en n8n

1. Abre n8n → menú **⋮** → **Import from File**.
2. Selecciona el archivo `.json` del workflow correspondiente.
3. Configura las credenciales (ver más abajo) — no vienen incluidas en el
   archivo exportado por seguridad.
4. Activa el workflow de consulta (el de carga se ejecuta manualmente).

### Credenciales necesarias en n8n

| Credencial | Tipo | Uso |
|---|---|---|
| MySQL account | MySQL | Nodo de lectura de cursos |
| OpenRouter (Header/Bearer Auth) | Generic Credential | Embeddings y generación de respuesta |

Ninguna API key va escrita directamente en los workflows — se configuran como
credenciales dentro de n8n.

## Docker

### Servicios

| Servicio | Imagen | Puerto |
|---|---|---|
| MySQL | `[PENDIENTE: confirmar imagen/versión usada por el equipo]` | 3306 |
| Qdrant | `qdrant/qdrant:latest` | 6333 (HTTP), 6334 (gRPC) |
| n8n | `n8nio/n8n:latest` | 5678 |
| `[PENDIENTE: backend/frontend si se dockerizan]` | | |

### Cómo levantar los servicios

```bash
docker compose up -d
```

Se emplea un archivo.env para almacenar las variables de entorno necesarias sin hacerlas publicas


Verificar que todo esté arriba:
```bash
docker ps
```

### Variables de entorno (`.env`)

```env
# MySQL
MYSQL_ROOT_PASSWORD=
MYSQL_DATABASE=RutaIA

# n8n (si se expone con ngrok)
NGROK_AUTHTOKEN=
N8N_HOST=
WEBHOOK_URL=

# OpenRouter (se configura como credencial DENTRO de n8n, no como variable de entorno)
```


