SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS cine_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE cine_db;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS venta;
DROP TABLE IF EXISTS sala;
DROP TABLE IF EXISTS pelicula;
DROP TABLE IF EXISTS producto_confiteria;
DROP TABLE IF EXISTS administrador;
DROP TABLE IF EXISTS empleado;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS membresia;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE membresia (
    id_membresia INT AUTO_INCREMENT PRIMARY KEY,
    tipo         VARCHAR(20)   NOT NULL UNIQUE,
    precio       DECIMAL(10,2) NOT NULL DEFAULT 0,
    CONSTRAINT ck_membresia_precio CHECK (precio >= 0)
) ENGINE = InnoDB;

CREATE TABLE usuario (
    documento INT          NOT NULL PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    edad      INT          NOT NULL,
    rol       ENUM('CLIENTE', 'EMPLEADO', 'ADMINISTRADOR') NOT NULL,
    CONSTRAINT ck_usuario_documento CHECK (documento > 0),
    CONSTRAINT ck_usuario_edad CHECK (edad BETWEEN 0 AND 120)
) ENGINE = InnoDB;

CREATE TABLE cliente (
    documento           INT NOT NULL PRIMARY KEY,
    visitas             INT NOT NULL DEFAULT 0,
    fecha_ultima_visita DATE NULL,
    id_membresia        INT NULL,
    CONSTRAINT ck_cliente_visitas CHECK (visitas >= 0),
    CONSTRAINT fk_cliente_usuario FOREIGN KEY (documento)
        REFERENCES usuario (documento) ON DELETE CASCADE,
    CONSTRAINT fk_cliente_membresia FOREIGN KEY (id_membresia)
        REFERENCES membresia (id_membresia) ON DELETE SET NULL
) ENGINE = InnoDB;

CREATE TABLE empleado (
    documento INT         NOT NULL PRIMARY KEY,
    cargo     VARCHAR(80) NOT NULL,
    CONSTRAINT fk_empleado_usuario FOREIGN KEY (documento)
        REFERENCES usuario (documento) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE administrador (
    documento INT         NOT NULL PRIMARY KEY,
    area      VARCHAR(80) NOT NULL,
    CONSTRAINT fk_administrador_usuario FOREIGN KEY (documento)
        REFERENCES usuario (documento) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE pelicula (
    id_pelicula   INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(120)  NOT NULL,
    genero        VARCHAR(60)   NOT NULL,
    duracion_min  INT           NOT NULL,
    clasificacion VARCHAR(30)   NOT NULL,
    precio_boleta DECIMAL(10,2) NOT NULL,
    CONSTRAINT ck_pelicula_duracion CHECK (duracion_min > 0),
    CONSTRAINT ck_pelicula_precio CHECK (precio_boleta >= 0)
) ENGINE = InnoDB;

CREATE TABLE sala (
    id_sala     INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(50) NOT NULL UNIQUE,
    capacidad   INT         NOT NULL,
    id_pelicula INT         NULL,
    CONSTRAINT ck_sala_capacidad CHECK (capacidad > 0),
    CONSTRAINT fk_sala_pelicula FOREIGN KEY (id_pelicula)
        REFERENCES pelicula (id_pelicula) ON DELETE SET NULL
) ENGINE = InnoDB;

CREATE TABLE producto_confiteria (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100)  NOT NULL,
    tipo        VARCHAR(40)   NOT NULL,
    precio      DECIMAL(10,2) NOT NULL,
    CONSTRAINT ck_producto_precio CHECK (precio >= 0)
) ENGINE = InnoDB;

CREATE TABLE venta (
    id_venta          INT AUTO_INCREMENT PRIMARY KEY,
    documento_cliente INT           NOT NULL,
    membresia         VARCHAR(20)   NOT NULL,
    tipo_venta        ENUM('BOLETA', 'CONFITERIA') NOT NULL,
    producto          VARCHAR(150)  NOT NULL,
    cantidad          INT           NOT NULL,
    precio_unitario   DECIMAL(10,2) NOT NULL,
    subtotal          DECIMAL(12,2) NOT NULL,
    descuento         DECIMAL(12,2) NOT NULL,
    total             DECIMAL(12,2) NOT NULL,
    fecha_venta       DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_venta_cantidad CHECK (cantidad > 0),
    CONSTRAINT fk_venta_cliente FOREIGN KEY (documento_cliente)
        REFERENCES cliente (documento),
    INDEX idx_venta_cliente (documento_cliente)
) ENGINE = InnoDB;

INSERT INTO membresia (tipo, precio) VALUES
    ('BASICA', 20000),
    ('PREMIUM', 0);

INSERT INTO usuario (documento, nombre, edad, rol) VALUES
    (1001, 'Juan',   25, 'CLIENTE'),
    (1002, 'Maria',  30, 'CLIENTE'),
    (1003, 'Carlos', 19, 'CLIENTE'),
    (2001, 'Laura',  28, 'EMPLEADO'),
    (3001, 'Andres', 40, 'ADMINISTRADOR');

INSERT INTO cliente (documento, visitas, fecha_ultima_visita, id_membresia) VALUES
    (1001, 29, DATE_SUB(CURDATE(), INTERVAL 1 DAY),
        (SELECT id_membresia FROM membresia WHERE tipo = 'BASICA')),
    (1002, 0, NULL, NULL),
    (1003, 0, NULL, NULL);

INSERT INTO empleado (documento, cargo) VALUES (2001, 'Taquillera');
INSERT INTO administrador (documento, area) VALUES (3001, 'Gerencia general');

INSERT INTO pelicula (nombre, genero, duracion_min, clasificacion, precio_boleta) VALUES
    ('Duna: Parte Dos', 'Ciencia ficción', 166, '12 años',      15000),
    ('Intensamente 2',  'Animación',        96, 'Todo público', 13000),
    ('Del Revés',       'Terror',          110, '15 años',      14000);

INSERT INTO sala (nombre, capacidad, id_pelicula) VALUES
    ('Sala 1',  80, (SELECT id_pelicula FROM pelicula WHERE nombre = 'Duna: Parte Dos')),
    ('Sala 2',  60, (SELECT id_pelicula FROM pelicula WHERE nombre = 'Intensamente 2')),
    ('Sala 3', 100, (SELECT id_pelicula FROM pelicula WHERE nombre = 'Del Revés'));

INSERT INTO producto_confiteria (nombre, tipo, precio) VALUES
    ('Crispetas grandes', 'Snack',  9000),
    ('Gaseosa mediana',   'Bebida', 6000),
    ('Combo pareja',      'Combo', 22000);
