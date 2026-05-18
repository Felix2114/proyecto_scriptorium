-- =============================================================================
-- Scriptorium - Seed inicial para entorno local de pruebas
--
-- Compatible con spring.jpa.hibernate.ddl-auto=update
-- =============================================================================

-- Esquema
CREATE TABLE IF NOT EXISTS bibliotecario (
    id_bibliotecario BIGSERIAL PRIMARY KEY,
    usuario          VARCHAR(100),
    contrasena       VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS genero (
    id_genero   BIGSERIAL PRIMARY KEY,
    descripcion VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS libro (
    id_libro  BIGSERIAL PRIMARY KEY,
    titulo    VARCHAR(100),
    autor     VARCHAR(200),
    isbn      VARCHAR(100),
    precio    DOUBLE PRECISION,
    genero_id BIGINT NOT NULL REFERENCES genero(id_genero)
);

CREATE TABLE IF NOT EXISTS inventario (
    id_inventario BIGSERIAL PRIMARY KEY,
    stock         INT,
    libro_id      BIGINT NOT NULL REFERENCES libro(id_libro)
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario       BIGSERIAL PRIMARY KEY,
    nombre           VARCHAR(50),
    direccion        VARCHAR(150),
    fecha_nacimiento DATE,
    contacto         VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS tipo_multa (
    id_tipo_multa BIGSERIAL PRIMARY KEY,
    tipo          VARCHAR(50),
    descripcion   VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS prestamo (
    id_prestamo      BIGSERIAL PRIMARY KEY,
    ficha            VARCHAR(50),
    activo           BOOLEAN,
    multado          BOOLEAN,
    devuelto         BOOLEAN,
    estado_prestamo  VARCHAR(50),
    estado_devuelto  VARCHAR(50),
    fecha_inicio     DATE,
    fecha_fin        DATE,
    usuario_id       BIGINT NOT NULL REFERENCES usuario(id_usuario),
    libro_id         BIGINT NOT NULL REFERENCES libro(id_libro),
    bibliotecario_id BIGINT NOT NULL REFERENCES bibliotecario(id_bibliotecario)
);

CREATE TABLE IF NOT EXISTS multa (
    id_multa        BIGSERIAL PRIMARY KEY,
    monto           REAL,
    fecha_multa     DATE,
    prestamo_id     BIGINT NOT NULL REFERENCES prestamo(id_prestamo),
    "tipo_multa_id" BIGINT NOT NULL REFERENCES tipo_multa(id_tipo_multa)
);

CREATE TABLE IF NOT EXISTS registro (
    id_registro      BIGSERIAL PRIMARY KEY,
    bibliotecario_id BIGINT NOT NULL REFERENCES bibliotecario(id_bibliotecario),
    dia_registro     DATE,
    hora_registro    TIME
);

-- Funcion de busqueda
CREATE OR REPLACE FUNCTION buscar_libros(palabra VARCHAR)
RETURNS TABLE (
    id_libro BIGINT, titulo VARCHAR, autor VARCHAR, isbn VARCHAR,
    precio DOUBLE PRECISION, genero_id BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT l.id_libro, l.titulo, l.autor, l.isbn, l.precio, l.genero_id
    FROM libro l
    WHERE LOWER(l.titulo) LIKE LOWER('%' || palabra || '%')
       OR LOWER(l.autor) LIKE LOWER('%' || palabra || '%')
       OR LOWER(l.isbn) LIKE LOWER('%' || palabra || '%');
END;
$$ LANGUAGE plpgsql;

-- Datos
INSERT INTO bibliotecario (usuario, contrasena) VALUES
    ('andryk', '12345678'),
    ('aldair', 'Aldair123'),
    ('emmanuel', 'Emma1234');

INSERT INTO genero (descripcion) VALUES
    ('Ficcion'),
    ('No ficcion'),
    ('Infantil'),
    ('Ciencia'),
    ('Historia');

INSERT INTO libro (titulo, autor, isbn, precio, genero_id) VALUES
    ('El Principito',           'Antoine de Saint-Exupery', '978-0156012195', 250.00, 3),
    ('Cien anos de soledad',    'Gabriel Garcia Marquez',   '978-0307474728', 320.00, 1),
    ('Don Quijote de la Mancha','Miguel de Cervantes',      '978-8420412146', 450.00, 1),
    ('Sapiens',                 'Yuval Noah Harari',        '978-0062316097', 380.00, 2),
    ('Breve historia del tiempo','Stephen Hawking',         '978-0553380163', 290.00, 4),
    ('1984',                    'George Orwell',            '978-0451524935', 220.00, 1),
    ('El nombre del viento',    'Patrick Rothfuss',         '978-0756404741', 410.00, 1);

INSERT INTO inventario (stock, libro_id) VALUES (5, 1), (3, 2), (4, 3), (2, 4), (3, 5), (6, 6), (2, 7);

INSERT INTO usuario (nombre, direccion, fecha_nacimiento, contacto) VALUES
    ('Laura Medina',     'Av. Constitucion 210, Queretaro', DATE '2000-05-15', '4421234567'),
    ('Carlos Hernandez', 'Calle Reforma 45, CDMX',          DATE '1995-08-22', '5559876543'),
    ('Ana Sofia Lopez',  'Blvd. Independencia 800, Monterrey', DATE '1998-11-03', '8181122334'),
    ('Diego Ramirez',    'Calle Hidalgo 150, Guadalajara',  DATE '1990-02-18', '3334567890'),
    ('Maria Torres',     'Av. Universidad 300, Puebla',     DATE '2002-07-09', '2225647890');

INSERT INTO tipo_multa (tipo, descripcion) VALUES
    ('Tardanza',        'Multa por devolucion tardia'),
    ('Dano al material','Multa por daño al material');

INSERT INTO prestamo (ficha, activo, multado, devuelto, estado_prestamo, estado_devuelto, fecha_inicio, fecha_fin, usuario_id, libro_id, bibliotecario_id)
VALUES ('PRES-001', true, false, false, 'activo', NULL, '2026-05-10', '2026-05-24', 1, 1, 1);
INSERT INTO prestamo (ficha, activo, multado, devuelto, estado_prestamo, estado_devuelto, fecha_inicio, fecha_fin, usuario_id, libro_id, bibliotecario_id)
VALUES ('PRES-002', true, true, false, 'activo', NULL, '2026-04-15', '2026-04-29', 2, 2, 1);
INSERT INTO prestamo (ficha, activo, multado, devuelto, estado_prestamo, estado_devuelto, fecha_inicio, fecha_fin, usuario_id, libro_id, bibliotecario_id)
VALUES ('PRES-003', true, true, false, 'activo', NULL, '2026-04-01', '2026-04-15', 3, 3, 2);
INSERT INTO prestamo (ficha, activo, multado, devuelto, estado_prestamo, estado_devuelto, fecha_inicio, fecha_fin, usuario_id, libro_id, bibliotecario_id)
VALUES ('PRES-004', true, false, false, 'activo', NULL, '2026-05-12', '2026-05-26', 4, 4, 2);
INSERT INTO prestamo (ficha, activo, multado, devuelto, estado_prestamo, estado_devuelto, fecha_inicio, fecha_fin, usuario_id, libro_id, bibliotecario_id)
VALUES ('PRES-005', false, false, true, 'completado', 'bueno', '2026-04-10', '2026-04-24', 5, 5, 1);
INSERT INTO prestamo (ficha, activo, multado, devuelto, estado_prestamo, estado_devuelto, fecha_inicio, fecha_fin, usuario_id, libro_id, bibliotecario_id)
VALUES ('PRES-006', false, false, true, 'completado', 'danado', '2026-03-20', '2026-04-03', 1, 6, 1);
INSERT INTO prestamo (ficha, activo, multado, devuelto, estado_prestamo, estado_devuelto, fecha_inicio, fecha_fin, usuario_id, libro_id, bibliotecario_id)
VALUES ('PRES-007', true, false, false, 'activo', NULL, '2026-05-11', '2026-05-25', 2, 7, 3);

INSERT INTO multa (monto, fecha_multa, prestamo_id, "tipo_multa_id") VALUES (95.00, '2026-04-30', 2, 1);
INSERT INTO multa (monto, fecha_multa, prestamo_id, "tipo_multa_id") VALUES (165.00, '2026-05-18', 3, 1);
INSERT INTO multa (monto, fecha_multa, prestamo_id, "tipo_multa_id") VALUES (50.00, '2026-04-05', 6, 2);

SELECT setval('bibliotecario_id_bibliotecario_seq', (SELECT COALESCE(MAX(id_bibliotecario), 1) FROM bibliotecario));
SELECT setval('genero_id_genero_seq', (SELECT COALESCE(MAX(id_genero), 1) FROM genero));
SELECT setval('libro_id_libro_seq', (SELECT COALESCE(MAX(id_libro), 1) FROM libro));
SELECT setval('inventario_id_inventario_seq', (SELECT COALESCE(MAX(id_inventario), 1) FROM inventario));
SELECT setval('usuario_id_usuario_seq', (SELECT COALESCE(MAX(id_usuario), 1) FROM usuario));
SELECT setval('tipo_multa_id_tipo_multa_seq', (SELECT COALESCE(MAX(id_tipo_multa), 1) FROM tipo_multa));
SELECT setval('prestamo_id_prestamo_seq', (SELECT COALESCE(MAX(id_prestamo), 1) FROM prestamo));
SELECT setval('multa_id_multa_seq', (SELECT COALESCE(MAX(id_multa), 1) FROM multa));
SELECT setval('registro_id_registro_seq', (SELECT COALESCE(MAX(id_registro), 1) FROM registro));
