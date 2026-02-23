DELIMITER $$

CREATE PROCEDURE crearPedidoExpress(IN folioParam int, IN claveHashParam VARCHAR(255), OUT idPedidoExpressParam INT)
BEGIN
    DECLARE idPedidoExpressVar int;
    
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al intentar crear el pedido Express';
    END;
    
    START TRANSACTION;
    INSERT INTO Pedidos(estado,idCliente, totalPagar)VALUES("Listo", null, 0.0);
    
    SET idPedidoExpressVar = LAST_INSERT_ID();
    
    INSERT INTO PedidoExpress(idPedidoExpress, folio, pinSeguridad)values(idPedidoExpressParam, folioParam, claveHashParam);
    
    SET idPedidoExpressParam= idPedidoExpressVar;
    
    COMMIT;
    
END $$
DELIMITER ;
