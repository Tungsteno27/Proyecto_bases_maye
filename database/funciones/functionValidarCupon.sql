DELIMITER $$

DROP FUNCTION IF EXISTS validarCupon$$

CREATE FUNCTION validarCupon (idCuponParam INT)
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    DECLARE usosRestantesVar INT;
    DECLARE usosActualesVar INT;
    DECLARE usosTotalesVar INT;
    DECLARE fechaFinVar TIMESTAMP;

    IF NOT EXISTS(
        SELECT 1 FROM Cupones
        WHERE idCupon = idCuponParam
    ) THEN 
        RETURN FALSE;
    END IF;

    SELECT usosActuales, usosTotales, fechaFin
    INTO usosActualesVar, usosTotalesVar, fechaFinVar
    FROM Cupones
    WHERE idCupon = idCuponParam;

    IF usosActualesVar >= usosTotalesVar THEN 
        RETURN FALSE;
    END IF;

    IF fechaFinVar IS NOT NULL AND NOW() > fechaFinVar THEN 
        RETURN FALSE;
    END IF;

    RETURN TRUE;
END$$
DELIMITER ;
