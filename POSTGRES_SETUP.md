# 🐘 PostgreSQL Local en Docker - Scriptorium

## ✅ Estado Actual

PostgreSQL 16 está corriendo localmente en Docker con la siguiente configuración:

```
Container:    scriptorium-postgres
Database:     scriptorium
User:         postgres
Password:     12345678
Host:         localhost
Port:         5432
```

## 📊 Tablas Creadas

- `bibliotecario` - Usuarios bibliotecarios
- `genero` - Géneros de libros
- `libro` - Catálogo de libros
- `inventario` - Stock de libros
- `usuario` - Miembros/usuarios de la biblioteca
- `prestamo` - Registro de préstamos
- `multa` - Registro de multas
- `tipo_multa` - Tipos de multa
- `registro` - Auditoría de registros

## 🔧 Configuración Spring Boot

El `application.properties` ya está configurado para conectarse automáticamente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/scriptorium?sslmode=disable
spring.datasource.username=postgres
spring.datasource.password=12345678
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 🚀 Comandos Útiles

### Iniciar PostgreSQL
```bash
cd /home/yesman/Documentos/proyecto_scriptorium
docker compose up -d
```

### Ver estado del contenedor
```bash
docker compose ps
```

### Ver logs de PostgreSQL
```bash
docker compose logs -f postgres
```

### Conectar a PostgreSQL directamente
```bash
docker exec -it scriptorium-postgres psql -U postgres -d scriptorium
```

### Ver todas las tablas
```bash
docker exec scriptorium-postgres psql -U postgres -d scriptorium -c "\dt"
```

### Ver estructura de una tabla
```bash
docker exec scriptorium-postgres psql -U postgres -d scriptorium -c "\d+ usuario"
```

### Ejecutar consulta SQL
```bash
docker exec scriptorium-postgres psql -U postgres -d scriptorium -c "SELECT * FROM bibliotecario;"
```

### Parar PostgreSQL
```bash
docker compose down
```

### Parar y limpiar volúmenes (borrar datos)
```bash
docker compose down -v
```

### Ver logs detallados
```bash
docker compose logs postgres
```

## 💾 Datos Iniciales

El script `docker/init.sql` se ejecuta automáticamente la primera vez que el contenedor arranca con un volumen vacío. Crea:

- Esquema de tablas
- Relaciones (foreign keys)
- Índices básicos

## 🐛 Solución de Problemas

### PostgreSQL no se conecta
1. Verificar que el contenedor está corriendo: `docker compose ps`
2. Verificar logs: `docker compose logs postgres`
3. Reiniciar: `docker compose restart postgres`

### Puerto 5432 ya en uso
```bash
# Cambiar el puerto en docker-compose.yml
ports:
  - "5433:5432"  # Cambiar el primer número
```

### Volumen con permisos incorrectos
```bash
docker compose down -v
docker compose up -d
```

## 📈 Monitoreo

### Conexiones activas
```bash
docker exec scriptorium-postgres psql -U postgres -d scriptorium -c "SELECT * FROM pg_stat_activity WHERE datname = 'scriptorium';"
```

### Tamaño de la base de datos
```bash
docker exec scriptorium-postgres psql -U postgres -d scriptorium -c "SELECT pg_size_pretty(pg_database_size('scriptorium'));"
```

## 🔄 Integración con Spring Boot

Cuando ejecutes la aplicación Spring Boot, automáticamente:

1. ✅ Se conectará a PostgreSQL en localhost:5432
2. ✅ Verificará/creará el esquema (mediante `hibernate.ddl-auto=update`)
3. ✅ Ejecutará las migraciones necesarias
4. ✅ Estará listo para tests E2E con Cypress

## 📝 Notas

- El volumen `postgres_data` persiste los datos entre reinicios
- El healthcheck verifica que PostgreSQL está listo cada 5 segundos
- Compatible con `spring.jpa.hibernate.ddl-auto=update`
