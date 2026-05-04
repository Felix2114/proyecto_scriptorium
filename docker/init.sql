-- =============================================================================
-- Scriptorium - Seed inicial para entorno local de pruebas (Cypress + manual)
--
-- Este script se ejecuta automaticamente la primera vez que el contenedor
-- arranca con un volumen vacio. Crea el esquema usando los mismos nombres que
-- Hibernate genera (snake_case) y siembra los datos minimos para que las
-- pruebas E2E pasen sin tener que registrar manualmente bibliotecarios, libros
-- ni miembros.
--
-- Compatible con spring.jpa.hibernate.ddl-auto=update: si las tablas ya existen
-- Hibernate no las recreara, solo agregara columnas que falten.
-- =============================================================================

-- ---------------------------------------------------------------------------
-- Esquema
-- ---------------------------------------------------------------------------
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
    id_multa     BIGSERIAL PRIMARY KEY,
    monto        REAL,
    fecha_multa  DATE,
    prestamo_id  BIGINT NOT NULL REFERENCES prestamo(id_prestamo),
    "tipoMulta_id" BIGINT NOT NULL REFERENCES tipo_multa(id_tipo_multa)
);

CREATE TABLE IF NOT EXISTS registro (
    id_registro      BIGSERIAL PRIMARY KEY,
    bibliotecario_id BIGINT NOT NULL REFERENCES bibliotecario(id_bibliotecario),
    dia_registro     DATE,
    hora_registro    TIME
);

-- ---------------------------------------------------------------------------
-- Funciones (para búsqueda)
-- ---------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION buscar_libros(palabra VARCHAR)
RETURNS TABLE (
    id_libro BIGINT,
    titulo VARCHAR,
    autor VARCHAR,
    isbn VARCHAR,
    precio DOUBLE PRECISION,
    genero_id BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        l.id_libro,
        l.titulo,
        l.autor,
        l.isbn,
        l.precio,
        l.genero_id
    FROM libro l
    WHERE LOWER(l.titulo) LIKE LOWER('%' || palabra || '%')
       OR LOWER(l.autor) LIKE LOWER('%' || palabra || '%')
       OR LOWER(l.isbn) LIKE LOWER('%' || palabra || '%');
END;
$$ LANGUAGE plpgsql;

-- ---------------------------------------------------------------------------
-- Datos seed
-- ---------------------------------------------------------------------------

-- Bibliotecario por defecto: andryk / 12345678 (usado por las pruebas Cypress)
INSERT INTO bibliotecario (usuario, contrasena) VALUES
    ('andryk', '12345678'),
    ('aldair', 'Aldair123'),
    ('emmanuel', 'Emma1234');

-- Generos
INSERT INTO genero (descripcion) VALUES
    ('Ficcion'),
    ('No ficcion'),
    ('Infantil'),
    ('Ciencia'),
    ('Historia');

-- Libros (al menos uno con "principito" para CP-CY-LIB-001)
INSERT INTO libro (titulo, autor, isbn, precio, genero_id) VALUES
    ('El Principito',           'Antoine de Saint-Exupery', '978-0156012195', 250.00, 3),
    ('Cien anos de soledad',    'Gabriel Garcia Marquez',   '978-0307474728', 320.00, 1),
    ('Don Quijote de la Mancha','Miguel de Cervantes',      '978-8420412146', 450.00, 1),
    ('Sapiens',                 'Yuval Noah Harari',        '978-0062316097', 380.00, 2),
    ('Breve historia del tiempo','Stephen Hawking',         '978-0553380163', 290.00, 4),
    ('1984',                    'George Orwell',            '978-0451524935', 220.00, 1),
    ('El nombre del viento',    'Patrick Rothfuss',         '978-0756404741', 410.00, 1);

-- Inventario (stock disponible para los prestamos)
INSERT INTO inventario (stock, libro_id) VALUES
    (5, 1),
    (3, 2),
    (4, 3),
    (2, 4),
    (3, 5),
    (6, 6),
    (2, 7);

-- Miembros (usuarios)
INSERT INTO usuario (nombre, direccion, fecha_nacimiento, contacto) VALUES
    ('Laura Medina',     'Av. Constitucion 210, Queretaro', DATE '2000-05-15', '4421234567'),
    ('Carlos Hernandez', 'Calle Reforma 45, CDMX',          DATE '1995-08-22', '5559876543'),
    ('Ana Sofia Lopez',  'Blvd. Independencia 800, Monterrey', DATE '1998-11-03', '8181122334'),
    ('Diego Ramirez',    'Calle Hidalgo 150, Guadalajara',  DATE '1990-02-18', '3334567890'),
    ('Maria Torres',     'Av. Universidad 300, Puebla',     DATE '2002-07-09', '2225647890');

-- Tipos de multa
INSERT INTO tipo_multa (tipo, descripcion) VALUES
    ('Tardanza',        'Multa por devolucion tardia'),
    ('Dano al material','Multa por daño al material');

-- Sincronizar las secuencias por si Hibernate inserta despues
SELECT setval('bibliotecario_id_bibliotecario_seq', (SELECT MAX(id_bibliotecario) FROM bibliotecario));
SELECT setval('genero_id_genero_seq',               (SELECT MAX(id_genero)        FROM genero));
SELECT setval('libro_id_libro_seq',                 (SELECT MAX(id_libro)         FROM libro));
SELECT setval('inventario_id_inventario_seq',       (SELECT MAX(id_inventario)    FROM inventario));
SELECT setval('usuario_id_usuario_seq',             (SELECT MAX(id_usuario)       FROM usuario));
SELECT setval('tipo_multa_id_tipo_multa_seq',       (SELECT MAX(id_tipo_multa)    FROM tipo_multa));
