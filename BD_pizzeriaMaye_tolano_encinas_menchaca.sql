#LA NUEVA BASE DE DATOS
drop database if exists mayepizzas;
CREATE DATABASE if not exists mayepizzas;
use mayepizzas;

CREATE TABLE Usuarios (
	idUsuario INT PRIMARY KEY AUTO_INCREMENT,
	correoElectronico VARCHAR(50) not null,
    hashContraseña VARCHAR(255) not null,
    rol ENUM("Empleado", "Cliente")
);

CREATE TABLE Empleados (
	idEmpleado INT PRIMARY KEY AUTO_INCREMENT,
	nombres VARCHAR(100) not null,
    apellidoPaterno VARCHAR(50) NOT NULL,
    apellidoMaterno VARCHAR(50) NULL,
    FOREIGN KEY(idEmpleado) REFERENCES Usuarios(idUsuario)
);

CREATE TABLE DireccionesClientes (
    idDireccion INT AUTO_INCREMENT PRIMARY KEY,
    calle VARCHAR(100),
    numero int,
    colonia VARCHAR(100)
);

CREATE TABLE Clientes (
	idCliente INT PRIMARY KEY AUTO_INCREMENT,
    nombres VARCHAR(100) NOT NULL,
    apellidoPaterno VARCHAR(50) NOT NULL,
    apellidoMaterno VARCHAR(50),
    fechaNacimiento DATE NOT NULL,
    estatus ENUM ("Activo", "Inactivo") DEFAULT "Activo",
    idDireccion INT, 
    FOREIGN KEY (idDireccion) REFERENCES DireccionesClientes(idDireccion),
    FOREIGN KEY(idCliente) REFERENCES Usuarios(idUsuario)
);

CREATE TABLE TelefonosClientes (
    idTelefono INT AUTO_INCREMENT PRIMARY KEY,
    idCliente INT NOT NULL,
    numero VARCHAR(15) NOT NULL,
    etiqueta VARCHAR(20),
    FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente)
);

CREATE TABLE Pedidos (
	idPedido INT AUTO_INCREMENT PRIMARY KEY,
    fechaCreacion DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    estado ENUM('Pendiente', 'En Preparación', 'Listo', 'Entregado', 'Cancelado', 'No Reclamado') DEFAULT 'Pendiente' NOT NULL,
    idCliente int NULL, #Pues en el pedido express no se vincula con ningún cliente
    totalPagar DECIMAL (10, 2) NOT NULL,
	FOREIGN KEY (idCliente) REFERENCES Clientes(idCliente)
);

CREATE TABLE Productos (
	idProducto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(75) not null,
    estado ENUM("Disponible", "No Disponible") NOT NULL DEFAULT "Disponible",
    precio DECIMAL (10,2) not null,
    descripcion VARCHAR(200) null,
    tamanio ENUM("Chica", "Mediana", "Grande") not null
);

CREATE TABLE PedidoProductos (
	idPedidoProducto INT AUTO_INCREMENT PRIMARY KEY,
    idPedido Int not null,
    idProducto Int not null,
    cantidad int not null,
    precio_unitario DECIMAL(10,2) not null,
    subTotal DECIMAL(10,2) not null,
    notas_adicionales VARCHAR(150) null,
    FOREIGN KEY (idPedido) REFERENCES Pedidos(idPedido),
    FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
);

CREATE TABLE Ingredientes (
	idIngrediente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) not null
);

CREATE TABLE ProductoIngredientes (
	idProductoIngrediente INT AUTO_INCREMENT PRIMARY KEY,
    idIngrediente int not null,
    idProducto int not null,
    FOREIGN KEY (idIngrediente) REFERENCES Ingredientes(idIngrediente),
    FOREIGN KEY (idProducto) REFERENCES Productos(idProducto)
);

CREATE TABLE Cupones (
	idCupon INT AUTO_INCREMENT PRIMARY KEY,
    usosTotales int not null default 0,
    usosActuales int not null,
    nombre VARCHAR(50) not null,
    porcentaje DECIMAL (5,2) not null,
    fechaInicio TIMESTAMP not null default current_timestamp,
    fechaFin TIMESTAMP NULL
);

CREATE TABLE PedidoProgramados (
	idPedidoProgramado INT AUTO_INCREMENT PRIMARY KEY,
    idCupon int null,
    FOREIGN KEY (idPedidoProgramado) REFERENCES Pedidos(idPedido),
    FOREIGN KEY (idCupon) REFERENCES Cupones(idCupon)
);

CREATE TABLE PedidoExpress (
	idPedidoExpress INT AUTO_INCREMENT PRIMARY KEY,
    folio int not null,
    pinSeguridad VARCHAR(255) not null,
    FOREIGN KEY (idPedidoExpress) REFERENCES Pedidos(idPedido)
);

CREATE TABLE HistorialEstados (
    idHistorial INT AUTO_INCREMENT PRIMARY KEY,
    idPedido INT NOT NULL,
    estadoAnterior ENUM('Pendiente', 'En Preparación', 'Listo', 'Entregado', 'Cancelado', 'No Reclamado') NOT NULL,
    estadoNuevo ENUM('Pendiente', 'En Preparación', 'Listo', 'Entregado', 'Cancelado', 'No Reclamado')NOT NULL,
    fechaHoraCambio DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (idPedido) REFERENCES Pedidos(idPedido) #guardar quien hizo el cambio
);






