
CREATE DATABASE IF NOT EXISTS pizzeriaMaJu;
USE pizzeriaMaJu;

CREATE TABLE Cupones (
    id_cupon INT AUTO_INCREMENT PRIMARY KEY,
    nombre_cupon VARCHAR(50) NOT NULL UNIQUE,
    porcentaje_descuento DECIMAL(4,3) NOT NULL 
);

CREATE TABLE Direcciones_Clientes (
    id_direccion INT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(100),
    numero int,
    colonia VARCHAR(100)
);

CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50),
    fecha_nacimiento DATE NOT NULL,
    id_direccion INT, 
    FOREIGN KEY (id_direccion) REFERENCES Direcciones_Clientes(id_direccion)
);

CREATE TABLE Clientes_Cupones (
    id_cliente_cupon INT AUTO_INCREMENT PRIMARY KEY,
    id_cupon INT,
    id_cliente INT,
    fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_fin DATE,
    usos_restantes INT DEFAULT 1,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_cupon) REFERENCES Cupones(id_cupon)
);

CREATE TABLE Telefonos_Clientes (
    id_telefono INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    numero VARCHAR(15) NOT NULL,
    etiqueta VARCHAR(20),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE Pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    total_pagar DECIMAL(10, 2) DEFAULT 0.00 NOT NULL,
    estado ENUM('Pendiente', 'En Preparación', 'Listo', 'Entregado', 'Cancelado', 'No Reclamado') DEFAULT 'Pendiente' NOT NULL,
    id_cliente INT NULL, 
    id_cupon_aplicado INT NULL,
    id_telefono INT NULL,
    FOREIGN KEY (id_telefono) REFERENCES Telefonos_Clientes(id_telefono),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_cupon_aplicado) REFERENCES Cupones(id_cupon)
);

CREATE TABLE Pedidos_Programados (
    id_pedido_Programado INT PRIMARY KEY,
    FOREIGN KEY (id_pedido_Programado) REFERENCES Pedidos(id_pedido)
);

CREATE TABLE Pedidos_Express (
    id_pedido_express INT PRIMARY KEY, 
    folio VARCHAR(20) NOT NULL UNIQUE,
    pin_seguridad VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_pedido_express) REFERENCES Pedidos(id_pedido)
);

CREATE TABLE Historiales_Estados (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    estado_anterior ENUM('Pendiente', 'En Preparación', 'Listo', 'Entregado', 'Cancelado', 'No Reclamado') NOT NULL,
    estado_nuevo ENUM('Pendiente', 'En Preparación', 'Listo', 'Entregado', 'Cancelado', 'No Reclamado')NOT NULL,
    fecha_hora_cambio DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido)
);

CREATE TABLE Productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    tamanio ENUM('Chica', 'Mediana', 'Grande') NOT NULL,
    estado_disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE Detalles_Pedidos (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    notas_adicionales VARCHAR(200) NULL, 
    precio_unitario DECIMAL(10,2), 
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);







