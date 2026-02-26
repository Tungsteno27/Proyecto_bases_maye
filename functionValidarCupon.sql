
#Funcion que valida que un cupon sea valido
DELIMITER $$
CREATE FUNCTION validarCupon (idCuponClienteParam INT)
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
	DECLARE usosResVar INT;
    DECLARE fechaFinVar DATE;
    
	#Valida que exista el cupón dado por el parametro
    IF NOT EXISTS(
		SELECT 1 FROM Clientes_Cupones AS cc
        WHERE cc.id_cliente_cupon = idCuponClienteParam
    ) THEN RETURN FALSE;
    END IF;
    
    #Obtiene el número de usos que le quedan al cupón y luego valida si todavía le quedan
    SELECT usos_restantes
    INTO usosResVar
    FROM Clientes_Cupones
    where id_cliente_cupon = idCuponClienteParam;
    
    IF usosResVar = 0 THEN RETURN FALSE; 
    END IF;
    
    #Valida que la fecha del cupón sea válida (que el cupón no esté expirado)
    SELECT fecha_fin
    INTO  fechaFinVar
    FROM Clientes_Cupones
    where id_cliente_cupon = idCuponClienteParam;
    
    IF current_date() > fecha_fin THEN RETURN FALSE;
    END IF;
    
END $$
DELIMITER ;
