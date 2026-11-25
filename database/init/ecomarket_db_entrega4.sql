-- Base de datos de ECommerceAD-mj
-- Script para crear la base de datos y sus tablas para las entregas.
-- MariaDB / MySQL

CREATE DATABASE IF NOT EXISTS ecomarket_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE ecomarket_db;

-- Tabla de categorias
CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    activo BOOLEAN NOT NULL,
    categoria_id INT NULL,
    CONSTRAINT fk_productos_categorias
        FOREIGN KEY (categoria_id) REFERENCES categorias(id)
) ENGINE=InnoDB;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Tabla de pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
) ENGINE=InnoDB;

-- Tabla de detalle de los pedidos
CREATE TABLE IF NOT EXISTS detalle_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
) ENGINE=InnoDB;

-- Datos iniciales de ejemplo
INSERT IGNORE INTO categorias (id, nombre) VALUES
(1, 'Accesorios'),
(2, 'Perifericos');

INSERT IGNORE INTO clientes (id, nombre, email) VALUES
(1, 'Maria', 'maria@gmail.com');

INSERT IGNORE INTO productos (id, nombre, precio, stock, activo, categoria_id) VALUES
(1, 'Teclado', 25.50, 10, 1, 1),
(2, 'Raton', 15.00, 20, 1, 2);
