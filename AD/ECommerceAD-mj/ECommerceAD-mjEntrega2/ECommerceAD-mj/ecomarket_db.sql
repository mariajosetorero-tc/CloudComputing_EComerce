-- Base de datos para la Entrega 3 - JDBC
-- Compatible con MariaDB (XAMPP) y MySQL.
-- Ejecutar este archivo desde phpMyAdmin o desde un cliente SQL.

CREATE DATABASE IF NOT EXISTS ecomarket_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE ecomarket_db;

CREATE TABLE IF NOT EXISTS productos (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    activo BOOLEAN NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS detalle_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
) ENGINE=InnoDB;

-- Datos de prueba. INSERT IGNORE evita duplicados si se ejecuta más de una vez.
INSERT IGNORE INTO clientes (id, nombre, email)
VALUES (1, 'Maria', 'maria@gmail.com');

INSERT IGNORE INTO productos (id, nombre, precio, stock, activo)
VALUES
    (1, 'Teclado', 25.50, 10, 1),
    (2, 'Raton', 15.00, 20, 1);
