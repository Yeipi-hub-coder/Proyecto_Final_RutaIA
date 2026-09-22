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

