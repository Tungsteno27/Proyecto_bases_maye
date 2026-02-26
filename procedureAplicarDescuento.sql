#Procedimiento que aplica un cupón de descuento a el total de un pedido y resta un uso del cupón del cliente.
DELIMITER $$
CREATE PROCEDURE aplicarDesc( IN idPedidoParam int, IN idCuponClienteParam int)
BEGIN
	DECLARE porcentajeVariable DECIMAL(4,3);
    DECLARE idCuponVariable INT;
    
    #Este handler se usa para hacer el rollback en caso de que ocurra una excepción de sql, regresando todo a como estaba al inicio
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error al intentar aplicar el cupón';
    END;
    
    #E
	IF NOT validarCupon(idCuponClienteParam) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El cupón es inválido';
    END IF;
    
    #Extrae el porcentaje de descuento y el id del cupon de la tabla cupones y se los asigna a las variables
	SELECT c.porcentaje_descuento, c.id_cupon
    INTO porcentajeVariable, idCuponVariable
    FROM Cupones AS c
    INNER JOIN Clientes_Cupones AS cc 
        ON c.id_cupon = cc.id_cupon
    WHERE cc.id_cliente_cupon = idCuponClienteParam;
    
    #Actualiza la tabla de pedidos aplicandole el descuento al total_pagar y luego asigna el cupón que el cliente utilizó en id_cupon_aplicado
    UPDATE Pedidos
    SET total_pagar = total_pagar - (total_pagar * porcentajeVariable),
        id_cupon_aplicado = IdCuponClienteParam
    WHERE id_pedido = idPedidoParam;
    
    #Resta uno a los usos restantes del cupón utilizado
    UPDATE Clientes_Cupones
    set usos_restantes= usos_restantes -1
    where id_cliente_cupon= idCuponClienteParam;

END $$
DELIMITER ;


