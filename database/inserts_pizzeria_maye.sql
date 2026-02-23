INSERT INTO Usuarios (correoElectronico, hashContraseña, rol) VALUES
('juan.perez@gmail.com', '$2a$10$abcdefghijklmnopqrstuuVwxyz1234567890abcdefghijklmnop', 'Cliente'),
('maria.garcia@gmail.com', '$2a$10$abcdefghijklmnopqrstuuVwxyz1234567890abcdefghijklmnop', 'Cliente'),
('empleado1@pizzeria.com', '$2a$10$abcdefghijklmnopqrstuuVwxyz1234567890abcdefghijklmnop', 'Empleado');

-- Empleados
INSERT INTO Empleados (idEmpleado, nombres, apellidoPaterno, apellidoMaterno) VALUES
(3, 'Carlos', 'Lopez', 'Martinez');

-- Direcciones
INSERT INTO DireccionesClientes (calle, numero, colonia) VALUES
('Av. Insurgentes', 123, 'Centro'),
('Calle Reforma', 456, 'Del Valle');

-- Clientes
INSERT INTO Clientes (idCliente, nombres, apellidoPaterno, apellidoMaterno, fechaNacimiento, idDireccion) VALUES
(1, 'Juan', 'Perez', 'Gomez', '1995-04-12', 1),
(2, 'Maria', 'Garcia', 'Lopez', '1990-08-23', 2);

-- Telefonos
INSERT INTO TelefonosClientes (idCliente, numero, etiqueta) VALUES
(1, '6441234567', 'Casa'),
(1, '6449876543', 'Trabajo'),
(2, '6441112233', 'Casa');

-- Productos
INSERT INTO Productos (nombre, precio, descripcion, tamanio) VALUES
('Hawaiana', 120.00, 'Pizza con piña y jamón', 'Chica'),
('Pepperoni', 150.00, 'Pizza con pepperoni', 'Mediana'),
('Mexicana', 200.00, 'Pizza con jalapeño y chorizo', 'Grande');


-- Cupones
INSERT INTO Cupones (usosTotales, usosActuales, nombre, porcentaje, fechaInicio, fechaFin) VALUES
(10, 0, 'PROMO10', 10.00, '2025-01-01', '2026-12-31'),   -- Válido
(5, 5, 'EXPIRADO', 15.00, '2024-01-01', '2024-06-01'),   -- Fecha expirada
(3, 3, 'AGOTADO', 20.00, '2025-01-01', '2026-12-31');    -- Usos agotados

-- Pedidos
INSERT INTO Pedidos (estado, idCliente, totalPagar) VALUES
('Pendiente', 1, 0.00),  -- idPedido = 1, programado de Juan
('Pendiente', 2, 0.00),  -- idPedido = 2, programado de Maria
('Pendiente', NULL, 0.00); -- idPedido = 3, express

-- PedidosProgramados
INSERT INTO PedidoProgramados (idPedidoProgramado, idCupon) VALUES
(1, NULL),
(2, NULL);

-- PedidoExpress
INSERT INTO PedidoExpress (idPedidoExpress, folio, pinSeguridad) VALUES
(3, 10001, '$2a$10$abcdefghijklmnopqrstuuVwxyz1234567890abcdefghijklmnop');

-- PedidoProductos
INSERT INTO PedidoProductos (idPedido, idProducto, cantidad, precio_unitario, subTotal, notas_adicionales) VALUES
(1, 1, 2, 120.00, 240.00, 'Sin piña por favor'),
(1, 2, 1, 150.00, 150.00, NULL),
(2, 3, 1, 200.00, 200.00, NULL),
(3, 2, 1, 150.00, 150.00, NULL);
