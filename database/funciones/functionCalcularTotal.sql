DELIMITER $$
DROP FUNCTION IF EXISTS calcularTotalPedido$$
CREATE FUNCTION calcularTotalPedido(idPedidoParam INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE totalVar DECIMAL(10,2);

    SELECT SUM(subTotal)
    INTO totalVar
    FROM PedidoProductos
    WHERE idPedido = idPedidoParam;

    RETURN totalVar;
END$$
DELIMITER ;
