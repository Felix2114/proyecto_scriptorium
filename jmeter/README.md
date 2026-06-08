# JMeter para Scriptorium

Los planes están pensados para el backend Spring Boot en `http://localhost:8080`.

## Archivos

- `scriptorium-smoke.jmx`: validación rápida de login y consultas `GET`.
- `scriptorium-crud-template.jmx`: flujo completo de creación, actualización y eliminación sobre los recursos principales.

## Requisitos

- Backend levantado y conectado a PostgreSQL.
- Apache JMeter 5.6 o superior.
- Si tu `jmeter --version` muestra `2.13`, usa la ruta con Docker de abajo.

## Ejecución

```bash
mkdir -p jmeter/results
```

```bash
jmeter -n -t jmeter/scriptorium-smoke.jmx -l jmeter/results/smoke.jtl
```

```bash
jmeter -n -t jmeter/scriptorium-crud-template.jmx -l jmeter/results/crud.jtl
```

## Si tu JMeter local es antiguo

La versión `2.13` no es compatible con este proyecto en Java 17. Usa JMeter 5.5 en Docker:

```bash
cd /home/yesman/Documentos/proyecto_scriptorium
rm -f jmeter/results/smoke.jtl jmeter/results/jmeter.log
rm -rf jmeter/results/smoke-report jmeter/results/jmeter-temp
mkdir -p jmeter/results/jmeter-temp
docker run --rm \
	--user $(id -u):$(id -g) \
	--network host \
	-v "$PWD/jmeter":/jmeter \
	justb4/jmeter:5.5 \
	-j /jmeter/results/jmeter.log \
	-n -t /jmeter/scriptorium-smoke.jmx \
	-l /jmeter/results/smoke.jtl \
	-e -o /jmeter/results/smoke-report \
	-Jjmeter.reportgenerator.temp_dir=/jmeter/results/jmeter-temp \
	-Djava.io.tmpdir=/jmeter/results/jmeter-temp
```

```bash
cd /home/yesman/Documentos/proyecto_scriptorium
rm -f jmeter/results/crud.jtl jmeter/results/jmeter.log
rm -rf jmeter/results/crud-report jmeter/results/jmeter-temp
mkdir -p jmeter/results/jmeter-temp
docker run --rm \
	--user $(id -u):$(id -g) \
	--network host \
	-v "$PWD/jmeter":/jmeter \
	justb4/jmeter:5.5 \
	-j /jmeter/results/jmeter.log \
	-n -t /jmeter/scriptorium-crud-template.jmx \
	-l /jmeter/results/crud.jtl \
	-e -o /jmeter/results/crud-report \
	-Jjmeter.reportgenerator.temp_dir=/jmeter/results/jmeter-temp \
	-Djava.io.tmpdir=/jmeter/results/jmeter-temp
```

## Cobertura

### Smoke

- `POST /bibliotecarios/login`
- `GET /libro`
- `GET /libro/buscar-libros`
- `GET /Usuarios`
- `GET /Usuarios/buscar-usuarios`
- `GET /Prestamo`
- `GET /Prestamo/buscar_prestamo`
- `GET /bibliotecarios`
- `GET /Genero`
- `GET /TipoMulta`
- `GET /Registro`
- `GET /inventario`
- `GET /Multa`

### CRUD template

- `POST`, `GET`, `PUT`, `DELETE` para `Genero`, `TipoMulta`, `bibliotecarios`, `Usuarios`, `libro`, `inventario`, `Registro`, `Prestamo` y `Multa`.
- Los IDs se extraen desde `$.data.*`, porque todas las respuestas están envueltas en `ApiResponse`.

## Nota

El plan CRUD asume que el backend permite encadenar altas y bajas en una misma sesión sin validaciones extra de negocio. Si alguna regla del dominio bloquea un borrado, ajusta el orden de los samplers o deja solo el plan smoke.