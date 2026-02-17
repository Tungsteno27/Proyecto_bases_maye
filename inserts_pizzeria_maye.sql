
#Profe Maye, este código fue sacado de claudeCode, para usar valores de prueba y ver como se comportaban los triggers y Procedures
-- Cupones
INSERT INTO Cupones (nombre_cupon, porcentaje_descuento) VALUES
('VERANO10', 0.100),
('BIENVENIDO', 0.150),
('FIDELIDAD20', 0.200);

-- Direcciones
INSERT INTO Direcciones_Clientes (calle, numero, colonia) VALUES
('Av. Insurgentes', 245, 'Roma Norte'),
('Calle Morelos', 12, 'Centro'),
('Blvd. Juárez', 789, 'Jardines del Valle');

-- Clientes
INSERT INTO Clientes (nombres, apellido_paterno, apellido_materno, fecha_nacimiento, id_direccion) VALUES
('Carlos', 'Hernández', 'López', '1990-05-14', 1),
('María', 'Gómez', 'Ruiz', '1985-11-23', 2),
('Sofía', 'Torres', NULL, '2000-03-08', 3);

-- Clientes_Cupones
INSERT INTO Clientes_Cupones (id_cupon, id_cliente, fecha_inicio, fecha_fin, usos_restantes) VALUES
(1, 1, '2026-01-01', '2026-06-30', 3),
(2, 2, '2026-02-01', '2026-03-01', 1),
(3, 3, '2026-02-10', '2026-12-31', 5);

-- Telefonos
INSERT INTO Telefonos_Clientes (id_cliente, numero, etiqueta) VALUES
(1, '5512345678', 'Casa'),
(1, '5598765432', 'Trabajo'),
(2, '5567891234', 'Personal'),
(3, '5543218765', 'Personal');

-- Pedidos
INSERT INTO Pedidos (fecha_creacion, total_pagar, estado, id_cliente, id_cupon_aplicado, id_telefono) VALUES
('2026-02-10 10:30:00', 150.00, 'Entregado', 1, 1, 1),
('2026-02-12 14:00:00', 200.00, 'En Preparación', 2, NULL, 3),
('2026-02-15 09:00:00', 89.50, 'Pendiente', 3, 3, 4),
('2026-02-17 11:00:00', 0.00, 'Pendiente', NULL, NULL, NULL);

-- Pedidos_Programados (apunta a un pedido existente)
INSERT INTO Pedidos_Programados (id_pedido_Programado) VALUES (3);

-- Pedidos_Express (apunta a pedidos distintos)
INSERT INTO Pedidos_Express (id_pedido_express, folio, pin_seguridad) VALUES
(1, 'EXP-2026-001', 'hashed_pin_1'),
(2, 'EXP-2026-002', 'hashed_pin_2');

-- Historiales_Estados
INSERT INTO Historiales_Estados (id_pedido, estado_anterior, estado_nuevo, fecha_hora_cambio) VALUES
(1, 'Pendiente', 'En Preparación', '2026-02-10 10:35:00'),
(1, 'En Preparación', 'Listo', '2026-02-10 11:00:00'),
(1, 'Listo', 'Entregado', '2026-02-10 11:20:00'),
(2, 'Pendiente', 'En Preparación', '2026-02-12 14:10:00');

-- Productos
INSERT INTO Productos (nombre, descripcion, precio, tamanio, estado_disponible) VALUES
('Pizza Margarita', 'Salsa de tomate, mozzarella y albahaca', 89.00, 'Chica', TRUE),
('Pizza Pepperoni', 'Salsa de tomate, mozzarella y pepperoni', 120.00, 'Mediana', TRUE),
('Pizza Hawaiana', 'Jamón, piña y mozzarella', 150.00, 'Grande', TRUE),
('Pizza BBQ', 'Salsa BBQ, pollo y cebolla morada', 135.00, 'Mediana', FALSE);

-- Detalles_Pedidos
INSERT INTO Detalles_Pedidos (id_pedido, id_producto, notas_adicionales, precio_unitario) VALUES
(1, 2, 'Sin cebolla', 120.00),
(1, 1, NULL, 89.00),
(2, 3, 'Extra queso', 150.00),
(3, 4, 'Sin salsa BBQ', 135.00);
