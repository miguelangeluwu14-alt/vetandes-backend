# VetAndes — Estructura basada en el ejemplo de PharmaSoft

El proyecto sigue el estilo estructural del backend de ejemplo `pharmaSoft (2).zip`, sin copiar sus entidades ni sus reglas del dominio.

- `controller`: endpoints `/api/v1` y respuestas HTTP.
- `service/service`: contratos de servicios.
- `service/impl`: implementaciones, reglas de negocio y transacciones.
- `repository`: acceso a datos de Oracle y reporte JPQL.
- `dto`: modelos de solicitudes/respuestas separados de las entidades.
- `mapper`: transformaciones de entidades a DTO.
- `entity`: entidades JPA del caso VetAndes.
- `config` y `security`: autenticación JWT, BCrypt y permisos.
- `exception`: errores uniformes y códigos 400/401/403/404/409.
- `db/migration/V1__esquema_y_semilla.sql`: esquema y datos exigidos por la guía.

El caso académico prohíbe conservar clases de Farmacia. No reutilizar nombres de Producto, Venta o Categoria en VetAndes.

## Importante

El ZIP se preparó y revisó estructuralmente, pero no se ha compilado con Maven ni se ha ejecutado contra Oracle aquí.
Configurar `ORACLE_URL`, `ORACLE_USER`, `ORACLE_PASSWORD` y `JWT_SECRET_BASE64` antes de ejecutar.
Usar `./mvnw spring-boot:run` (Linux/macOS) o `mvnw.cmd spring-boot:run` (Windows).
Ver `README.md` y `GUIA_DE_VERIFICACION.md` para requisitos adicionales.
