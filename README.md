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
4. Swagger UI (para probar RF03 - gestión de cursos como admin, y todo lo demás):
   ```
   http://localhost:8080/swagger-ui.html
   ```

## Contrato con n8n (RF08)

Spring Boot llama por POST a `n8n.webhook-url` con este body:

```json
{
  "consultaId": 10,
  "pregunta": "Quiero aprender a crear páginas web",
  "nivelExperiencia": "PRINCIPIANTE",
  "areaInteres": "Desarrollo web"
}
```

Y espera como respuesta (esto lo debe devolver tu workflow de n8n, después de
generar el embedding, consultar Qdrant y llamar al modelo vía OpenRouter):

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
- El umbral de similitud (RF11) se aplica **dentro de n8n**, antes de construir el
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

| Método | Endpoint                              | RF     |
|--------|----------------------------------------|--------|
| POST   | /api/estudiantes                       | RF01   |
| GET    | /api/estudiantes                       | RF02   |
| GET    | /api/estudiantes/{id}                  | RF02   |
| GET    | /api/estudiantes/{id}/historial        | RF02/16|
| POST   | /api/cursos                            | RF03   |
| PUT    | /api/cursos/{id}                       | RF03   |
| PATCH  | /api/cursos/{id}/desactivar            | RF03   |
| GET    | /api/cursos?categoria=&nivel=          | RF04   |
| POST   | /api/consultas                         | RF06-14|
| GET    | /api/consultas/{id}                    | RF15   |
| POST   | /api/calificaciones                    | RF17   |
| GET    | /api/estadisticas                      | RF18   |

## Pendiente por hacer tú (fuera del alcance de este backend)
- El workflow de n8n (RF05, RF08-RF12): vectorizar cursos, generar embedding de la
  pregunta, consultar Qdrant, aplicar el umbral y construir el prompt RAG.
- El frontend (HTML/CSS/JS) que consuma esta API.
- Pruebas funcionales y documentación adicional en Swagger (`@Operation`, ejemplos).
