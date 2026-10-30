# Verificación antes de la defensa

Esta lista reproduce y concreta los criterios de las páginas 7–15 del examen.

- [ ] `mvn clean test` termina sin errores.
- [ ] Flyway aplica `V1__esquema_y_semilla.sql` en un esquema Oracle vacío.
- [ ] Hay 5 propietarios, 9 prestaciones (una inactiva), 8 mascotas, 3 atenciones REGISTRADAS y 1 ANULADA.
- [ ] Los 4 usuarios existen; sus passwords empiezan por `$2a$` o `$2b$` (BCrypt) tras arrancar.
- [ ] `GET /api/v1/prestaciones` con token responde 200.
- [ ] `POST /api/v1/prestaciones` responde 201 y registro duplicado 409.
- [ ] `PUT /api/v1/prestaciones/{id}` funciona y `DELETE` de prestación usada responde 409.
- [ ] `PATCH /api/v1/prestaciones/{id}/estado` desactiva prestación usada.
- [ ] `POST /api/v1/mascotas` responde 201; duplicado para mismo propietario responde 409.
- [ ] `POST /api/v1/mascotas` sin propietario válido responde 404; validación responde 400 con `campos`.
- [ ] Registro con 3 prestaciones distintas aplica 10% en el backend y no acepta total del cliente.
- [ ] Error en el tercer detalle (especie no aplicable) devuelve 409 y no deja cabecera ni detalle en Oracle.
- [ ] Anulación fuera de 48 h devuelve 409; motivo de 9 caracteres devuelve 400.
- [ ] Filtros combinados y listado ordenado por fecha descendente funcionan.
- [ ] El reporte usa JPQL `GROUP BY` y excluye `ANULADA`.
- [ ] `OPTIONS` de CORS permite origen Angular 4200 y `Authorization`.
- [ ] 401 sin token, 401 con token caducado o modificado, 403 con token de rol sin permiso.
- [ ] Swagger UI carga y permite autenticación Bearer.
- [ ] `GET /api/v1/auth/me` devuelve usuario y permisos sin contraseña.
- [ ] Repositorios Git, commits, etiqueta, PDF de capturas y README listos (estos últimos dependen de su ejecución local).

## SQL de ayuda para evidencias

```sql
SELECT COUNT(*) FROM propietario;
SELECT COUNT(*) FROM prestacion;
SELECT COUNT(*) FROM mascota;
SELECT estado, COUNT(*) FROM atencion GROUP BY estado;
SELECT a.numero, a.total, a.descuento, d.cantidad, d.subtotal
FROM atencion a JOIN detalle_atencion d ON d.atencion_id=a.id
ORDER BY a.numero,d.id;
SELECT username,password,activo FROM usuarios;
SELECT version,success FROM flyway_schema_history;
```
