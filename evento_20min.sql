use mayepizzas;

SET GLOBAL event_scheduler = ON;

CREATE EVENT verificarPedidosNoReclamados
ON SCHEDULE EVERY 1 MINUTE
DO
UPDATE Pedidos
SET estado = 'No Reclamado'
WHERE estado = 'Listo'
AND fechaCreacion <= NOW() - INTERVAL 20 MINUTE;
