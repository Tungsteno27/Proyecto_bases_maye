DELIMITER $$
#Aquí faltaría insertar manualmente quien hizo el cambio de estado
DROP TRIGGER IF EXISTS registrarCambioEstados$$

CREATE TRIGGER registrarCambioEstados
AFTER UPDATE ON Pedidos
FOR EACH ROW
BEGIN
    IF OLD.estado != NEW.estado THEN
        INSERT INTO HistorialEstados (
            idPedido,
            estadoAnterior,
            estadoNuevo,
            fechaHoraCambio
        )
        VALUES (
            NEW.idPedido,
            OLD.estado,
            NEW.estado,
            NOW()
        );
    END IF;
END$$

DELIMITER ;
