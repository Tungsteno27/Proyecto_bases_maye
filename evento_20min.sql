drop event verificarPedidosNoReclamados;
SET GLOBAL event_scheduler = ON;
CREATE EVENT verificarPedidosNoReclamados
ON SCHEDULE EVERY 1 MINUTE
DO
UPDATE Pedidos p
SET p.estado = 'No Reclamado'
WHERE p.estado = 'Listo'
AND p.idCliente IS NULL
AND p.fechaCreacion <= NOW() - INTERVAL 20 MINUTE;
;
