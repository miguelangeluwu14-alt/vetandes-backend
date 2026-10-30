# VetAndes API — Examen Parcial II (Versión B)

**Implementación del BACKEND exclusivamente** para el caso VetAndes. Incluye Preguntas 1, 2 y 3 de la guía (12 puntos). La SPA Angular de las preguntas 4 y 5 no forma parte de este ZIP.

## Requisitos

- Java 21 o superior compatible con Spring Boot 4.
- Maven 3.9 o superior instalado localmente.
- Oracle Database con una **PDB / servicio accesible** y un **esquema vacío** cuyo usuario pueda crear tablas y secuencias.
- Acceso a Maven Central para descargar dependencias en la primera compilación.

Tecnologías usadas: Spring Boot **4.0.9**, Spring Data JPA, Spring Security, Bean Validation, Flyway, Oracle JDBC, JJWT **0.13.0** y springdoc OpenAPI **3.0.3**. Los números de parche son elecciones de implementación complementarias al PDF, que solo exige Spring Boot 4.

## 1. Preparación de Oracle

En SQL Developer conectado como administrador de la PDB, ejemplo para un entorno **local de práctica**:

```sql
CREATE USER VETANDES IDENTIFIED BY "ClaveLocalSegura2026*";
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE TO VETANDES;
ALTER USER VETANDES QUOTA UNLIMITED ON USERS;
```

**Importante:** `XEPDB1`, usuario y contraseña son ejemplos, no datos proporcionados por el examen. Si usa Oracle Free puede ser `FREEPDB1`; revise su instalación. Elija un esquema vacío: Flyway crea todas las tablas automáticamente al iniciar.

## 2. Variables de entorno

En PowerShell:

```powershell
$env:ORACLE_URL="jdbc:oracle:thin:@//localhost:1521/XEPDB1"
$env:ORACLE_USER="VETANDES"
$env:ORACLE_PASSWORD="ClaveLocalSegura2026*"
# 48 bytes aleatorios; el secreto NO debe subirse a Git
$bytes = New-Object byte[] 48
[System.Security.Cryptography.RandomNumberGenerator]::Fill($bytes)
$env:JWT_SECRET_BASE64=[Convert]::ToBase64String($bytes)
```

En Linux/macOS:

```bash
export ORACLE_URL='jdbc:oracle:thin:@//localhost:1521/XEPDB1'
export ORACLE_USER='VETANDES'
export ORACLE_PASSWORD='TU_CLAVE'
export JWT_SECRET_BASE64="$(openssl rand -base64 48 | tr -d '\n')"
```

No guarde los secretos dentro de `application.properties` ni en el repositorio.

## 3. Compilar y ejecutar

```bash
mvn clean test
mvn spring-boot:run
```

Swagger: http://localhost:8080/swagger-ui.html

La migración `src/main/resources/db/migration/V1__esquema_y_semilla.sql` crea las cinco tablas del anexo, tres tablas de seguridad, una secuencia y los datos semilla. Hibernate usa `ddl-auto=validate`: la estructura la controla Flyway, no Hibernate.

**Advertencia:** El anexo del PDF solo especifica la semilla por cantidades y contiene DDL de cinco tablas; las sentencias de inserción y el DDL de seguridad fueron desarrollados como complemento y se identifican en comentarios SQL. No se atribuyen al docente datos inventados.

## 4. Usuarios de prueba

| Usuario | Contraseña | Rol | Estado |
|---|---|---|---|
| admin | Vet2026* | ADMIN | activo |
| recepcion | Vet2026* | RECEPCIONISTA | activo |
| veterinario | Vet2026* | VETERINARIO | activo |
| inactivo | Vet2026* | RECEPCIONISTA | inactivo |

Los campos `password` comienzan NULL solo durante la migración y `InicializadorUsuarios` los guarda con **BCrypt** al iniciar. Si la contraseña ya está cifrada, no se modifica. Esta inicialización es únicamente para los usuarios de prueba exigidos por la guía; reemplazar credenciales antes de exponer la API.

Login:

```http
POST /api/v1/auth/login
Content-Type: application/json

{"username":"recepcion","password":"Vet2026*"}
```

Responde `200` con `token`, `tipo: Bearer`, `expiraEnSegundos: 3600`, nombre, roles y permisos. Para las otras rutas adjunte `Authorization: Bearer <token>`.

## 5. Contrato de rutas (base /api/v1)

| Método | Ruta | Permiso |
|---|---|---|
| POST | /auth/login | Público |
| GET | /auth/me | autenticado |
| GET | /propietarios | MASCOTA_LEER |
| GET, POST | /prestaciones?especie | PRESTACION_LEER / PRESTACION_ESCRIBIR |
| GET, PUT, DELETE | /prestaciones/{id} | PRESTACION_LEER / PRESTACION_ESCRIBIR |
| PATCH | /prestaciones/{id}/estado | PRESTACION_ESCRIBIR |
| GET, POST | /mascotas?propietarioId | MASCOTA_LEER / MASCOTA_ESCRIBIR |
| GET, PUT, DELETE | /mascotas/{id} | MASCOTA_LEER / MASCOTA_ESCRIBIR |
| POST | /atenciones | ATENCION_REGISTRAR |
| GET | /atenciones?mascota&especie&estado&desde&hasta | ATENCION_LEER |
| GET | /atenciones/{id} | ATENCION_LEER |
| PATCH | /atenciones/{id}/anular | ATENCION_ANULAR |
| GET | /reportes/atenciones-por-especie?desde&hasta | REPORTE_VER |

**CORS:** `http://localhost:4200`, `GET POST PUT PATCH DELETE OPTIONS`, encabezado `Authorization`.

### Prestación

```json
{"codigo":"PRS-010","nombre":"Consulta nutricional","precio":40.00,"especieAplicable":"TODAS","activo":true}
```

### Mascota

```json
{"nombre":"Pelusa","especie":"GATO","raza":"Mestiza","fechaNacimiento":"2024-05-12","sexo":"H","propietarioId":1}
```

### Atención

```json
{"mascotaId":1,"peso":12.50,"diagnostico":"Atención preventiva","detalles":[{"prestacionId":1,"cantidad":1},{"prestacionId":2,"cantidad":1},{"prestacionId":4,"cantidad":1}]}
```

Atención: `@Transactional` en servicio, entre 1 y 8 detalles, cantidad 1–3, producto único por atención, prestación activa y compatible con la especie, precio tomado desde Oracle, descuento del 10% cuando haya 3 o más prestaciones distintas. **El servidor ignora cualquier descuento o total enviado** porque el DTO no los declara.

Anulación:

```json
{"motivo":"Solicitud del propietario por cambio de horario"}
```

Solo se permite sobre `REGISTRADA`, hasta 48 horas desde su fecha y con motivo de 10 a 200 caracteres.

### Errores

Todos los errores diseñados de aplicación tienen `timestamp`, `status`, `error`, `mensaje`, `ruta`; los errores de validación también `campos`. Los códigos esperados son `400`, `401`, `403`, `404`, `409` y `500`.

## 6. Probar en Postman

Importe `postman/VetAndes_Backend.postman_collection.json`. Primero ejecute **Login recepcion** o **Login admin**; el script guarda `token` automáticamente. La colección contiene casos 200/201, 400, 401, 403, 404 y 409 y filtros/reportes. Ajuste IDs si los valores reales del esquema son diferentes.

**Prueba de rollback (Pregunta 2):** ingrese como RECEPCIONISTA; registre una atención con tres prestaciones distintas donde la tercera es `PRS-003` (solo GATO) y la mascota es `Luna` (PERRO). Debe responder 409. La transacción guarda tentativamente cabecera y primeras líneas, luego revierte todo. En Oracle compruebe que no exista esa atención ni detalles; la secuencia puede dejar huecos (normal).

**Prueba de reporte:** login VETERINARIO, GET `/reportes/atenciones-por-especie`, verifica que la atención ANULADA no cuente.

**Prueba de permisos:** con token RECEPCIONISTA, PATCH `/atenciones/1/anular` y GET `/reportes/atenciones-por-especie` → 403; con token VETERINARIO, POST `/mascotas` → 403; sin token → 401; token alterado → 401.

## 7. Git (requerimiento de la guía)

Crear repositorio `vetandes-backend` con `main`, `develop` y rama `examen-b-<apellido>`; registrar un commit por cada pregunta y al menos seis entre ambos repositorios. Etiqueta en ambos repositorios: `v1.0-examen-u2`. **El ZIP no finge incluir commits que no se hayan creado**.

## 8. Trazabilidad de los requisitos

- P1: paquetes `entity`, `repository`, `dto`, `mapper`, `service`, `controller`, `exception`, migración y Swagger.
- P2: `AtencionService`, `ReporteRepository` (JPQL `GROUP BY`), `ReporteService`, `CorsConfig`.
- P3: `AuthService`, `UsuarioDetailsService`, `JwtService`, `JwtAuthenticationFilter`, `SecurityConfig`, `PermisosPorRol`, control 401/403.

## 9. Límites de validación en este entorno

El proyecto fue revisado estáticamente; **no se ha probado la compilación Maven ni ejecutado contra Oracle** porque el entorno de generación no dispone de Maven ni de una instancia Oracle conectada. Antes de entregar, ejecute `mvn clean test`, arranque la aplicación y demuestre los endpoints en Swagger y SQL Developer. El docente evalúa el resultado funcionando, no solamente el archivo ZIP. Consulte `GUIA_DE_VERIFICACION.md`.
