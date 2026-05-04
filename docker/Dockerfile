FROM postgres:16

# Datos de conexion por defecto (alineados con scriptorium/src/main/resources/application.properties)
ENV POSTGRES_USER=postgres \
    POSTGRES_PASSWORD=12345678 \
    POSTGRES_DB=scriptorium

# El directorio /docker-entrypoint-initdb.d/ ejecuta cualquier *.sql / *.sh
# automaticamente la PRIMERA vez que el contenedor arranca con un volumen vacio.
COPY init.sql /docker-entrypoint-initdb.d/00-init.sql

EXPOSE 5432

# Healthcheck para que docker / orquestadores sepan cuando esta listo
HEALTHCHECK --interval=5s --timeout=3s --retries=10 \
  CMD pg_isready -U "$POSTGRES_USER" -d "$POSTGRES_DB" || exit 1
