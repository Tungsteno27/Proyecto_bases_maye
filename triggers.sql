DELIMITER $$
#Checar mañana en la mañana y crear los datos de prueba
DROP TRIGGER IF EXISTS registrarCambioEstados$$

CREATE TRIGGER registrarCambioEstados
AFTER UPDATE ON Pedidos
FOR EACH ROW
BEGIN
    IF OLD.estado != NEW.estado THEN
        INSERT INTO Historiales_Estados (
            id_pedido,
            estado_anterior,
            estado_nuevo,
            fecha_hora_cambio
        )
        VALUES (
            NEW.id_pedido,
            OLD.estado,
            NEW.estado,
            NOW()
        );
    END IF;
END$$

DELIMITER ;