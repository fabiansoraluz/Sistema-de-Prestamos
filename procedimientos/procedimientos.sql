DELIMITER //

CREATE PROCEDURE EliminarPrestamistaParaJefeGrupo(IN p_id_prestamista INT)
BEGIN
    -- Declarar variables para id_usuario e id_persona
    DECLARE v_id_usuario INT;
    DECLARE v_id_persona INT;
    
    -- Obtener el id_usuario y id_persona correspondientes al id_prestamista
    SELECT p.id_usuario, u.id_persona INTO v_id_usuario, v_id_persona
    FROM tb_prestamista p
    JOIN tb_usuario u ON p.id_usuario = u.id_usuario
    WHERE p.id_prestamista = p_id_prestamista
    LIMIT 1;

    -- Verificar si el prestamista tiene algún préstamo asociado
    IF EXISTS (SELECT 1 FROM tb_prestamo WHERE id_prestamista = p_id_prestamista) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede eliminar el prestamista, tiene préstamos asociados.';
    ELSE
        -- Eliminar de la tabla tb_prestamista
        DELETE FROM tb_prestamista WHERE id_prestamista = p_id_prestamista;
        
        -- Eliminar de la tabla tb_usuario
        DELETE FROM tb_usuario WHERE id_usuario = v_id_usuario;

        -- Eliminar de la tabla tb_persona
        DELETE FROM tb_persona WHERE id_persona = v_id_persona;
    END IF;
    
END//

DELIMITER ;

DELIMITER //

CREATE PROCEDURE eliminarJefeGrupo(IN p_id_usuario INT)
BEGIN
    DECLARE v_id_grupo INT;

    -- Obtener el id_grupo asociado al jefe de grupo
    SELECT id_grupo INTO v_id_grupo FROM tb_grupo WHERE id_jefe = p_id_usuario;

    -- Verificar si existen prestamistas asociados a ese grupo
    IF EXISTS (SELECT 1 FROM tb_prestamista WHERE id_grupo = v_id_grupo) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede eliminar el jefe de grupo, ya que tiene prestamistas asociados.';
    ELSE
        -- Eliminar de la tabla tb_grupo
        DELETE FROM tb_grupo WHERE id_jefe = p_id_usuario;

        -- Eliminar de la tabla tb_usuario
        DELETE FROM tb_usuario WHERE id_usuario = p_id_usuario;

        -- Eliminar de la tabla tb_persona
        DELETE FROM tb_persona WHERE id_persona = (SELECT id_persona FROM tb_usuario WHERE id_usuario = p_id_usuario);
    END IF;
END//

DELIMITER ;



DELIMITER //

CREATE PROCEDURE listar_grupos()
BEGIN
    SELECT
        g.id_grupo,
        u.username,
        p.nombre,
        p.paterno,
        p.materno,
        o.distrito,
        g.estado,
        p.id_persona,
        o.id_ubigeo
    FROM tb_grupo g
    JOIN tb_usuario u ON g.id_jefe = u.id_usuario
    JOIN tb_persona p ON u.id_persona = p.id_persona
    JOIN tb_ubigeo o ON o.id_ubigeo = g.id_ubigeo;
END//

DELIMITER ;


    
DELIMITER //

CREATE PROCEDURE ObtenerInformacionGrupo(IN idGrupo INT)
BEGIN
    SELECT 
        g.id_grupo,
        u.username,
        pr.nombre,
        pr.paterno,
        pr.materno,
        pr.celular,
        o.distrito,
        p.id_prestamista
    FROM tb_prestamista p 
    JOIN tb_usuario u ON p.id_usuario = u.id_usuario 
    JOIN tb_grupo g ON g.id_grupo = p.id_grupo 
    JOIN tb_persona pr ON pr.id_persona = u.id_persona
    JOIN tb_ubigeo o ON o.id_ubigeo = pr.id_ubigeo
    WHERE p.id_grupo = idGrupo;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE ObtenerSolicitudesPorGrupo(IN id_grupo INT)
BEGIN
    SELECT s.id_solicitud, CONCAT(p.nombre, ' ', p.paterno, ' ', p.materno) as prestatario, s.monto, s.fec_solicitud, cs.banco, s.estado
    FROM tb_solicitud s 
    INNER JOIN tb_cliente c ON c.id_cliente = s.id_cliente 
    INNER JOIN tb_persona p ON c.id_persona = p.id_persona 
    INNER JOIN tb_usuario u ON u.id_persona = p.id_persona 
    INNER JOIN tb_cuentas_bancarias cs ON s.id_cuenta = cs.id_cuenta
    WHERE s.id_grupo = id_grupo;
END //


DELIMITER ;


DELIMITER //


CREATE PROCEDURE ObtenerSolicitudesPorUsuario(IN IdUsuario INT)
BEGIN

    SELECT s.id_solicitud, s.observacion,  s.fec_solicitud, s.dias, s.monto , cb.nombre, s.estado, s.id_cliente, s.id_cuenta, s.id_grupo
    FROM tb_solicitud s
    INNER JOIN tb_cuentas_bancarias cb ON cb.id_cuenta = s.id_cuenta
    INNER JOIN tb_cliente c ON c.id_cliente = s.id_cliente
    INNER JOIN tb_persona p ON c.id_persona = p.id_persona
    INNER JOIN tb_usuario u ON u.id_persona = p.id_persona
    WHERE u.id_usuario = IdUsuario;
END //


DELIMITER ;


DELIMITER //
    
CREATE PROCEDURE idUltimaSolicitud()
BEGIN
	SELECT id_solicitud FROM tb_solicitud ORDER BY id_solicitud DESC LIMIT 1;
END //
    
DELIMITER ;

DELIMITER //

CREATE PROCEDURE ObtenerInformacionPrestamos(
    IN p_id_usuario INT,
    IN p_id_prestamo INT
)
BEGIN
    SELECT 
        c.id_prestamo,
        c.id_cuota,
        CONCAT(per.nombre, ' ', per.paterno, ' ', per.materno) AS nombre,
        c.numero,
        c.monto,
        c.monto_pagado,
        DATE_ADD(c.fec_pago, INTERVAL 1 DAY) AS fecha_vencimiento,
        c.estado
    FROM 
        tb_cuota c
    JOIN 
        tb_prestamo p ON c.id_prestamo = p.id_prestamo
    JOIN 
        tb_solicitud s ON p.id_solicitud = s.id_solicitud
    JOIN 
        tb_cliente cl ON s.id_cliente = cl.id_cliente
    JOIN 
        tb_persona per ON cl.id_persona = per.id_persona
    JOIN
        tb_prestamista pr ON p.id_prestamista = pr.id_prestamista
    WHERE
        pr.id_usuario = p_id_usuario AND p.id_prestamo = p_id_prestamo;
END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE ObtenerInformacionCuotas(IN p_id_cuota INT)
BEGIN
        SELECT 
        c.id_prestamo,
        c.id_cuota,
        c.estado,
        CONCAT(per.nombre, ' ', per.paterno, ' ', per.materno) AS nombre,
        c.numero,
        c.monto,
        c.monto_pagado,
        DATE_ADD(c.fec_pago, INTERVAL 1 DAY) AS fecha_vencimiento
    FROM 
        tb_cuota c
    JOIN 
        tb_prestamo p ON c.id_prestamo = p.id_prestamo
    JOIN 
        tb_solicitud s ON p.id_solicitud = s.id_solicitud
    JOIN 
        tb_cliente cl ON s.id_cliente = cl.id_cliente
    JOIN 
        tb_persona per ON cl.id_persona = per.id_persona
    JOIN
        tb_prestamista pr ON p.id_prestamista = pr.id_prestamista
    WHERE
        c.id_cuota = p_id_cuota;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE registrar_pago_cuota(
    IN p_id_cuota INT,
    IN p_nuevo_monto_pagado DOUBLE,
    IN p_nuevo_estado INT
)
BEGIN
    -- Iniciar transacción
    START TRANSACTION;

    -- Actualizar el monto pagado y el estado de la cuota
    UPDATE tb_cuota
    SET monto_pagado = p_nuevo_monto_pagado,
        estado = p_nuevo_estado
    WHERE id_cuota = p_id_cuota;

    -- Puedes agregar más lógica o validaciones si es necesario

    -- Confirmar la transacción
    COMMIT;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE ObtenerSolicitudesFiltradas(
  IN id_grupo INT,
  IN prestatario_nombre VARCHAR(255),
  IN estado INT
)
BEGIN
    SELECT 
        s.id_solicitud, 
        CONCAT(p.nombre, ' ', p.paterno, ' ', p.materno) as prestatario, 
        s.monto, 
        s.fec_solicitud, 
        cs.banco, 
        s.estado
    FROM tb_solicitud s 
    INNER JOIN tb_cliente c ON c.id_cliente = s.id_cliente 
    INNER JOIN tb_persona p ON c.id_persona = p.id_persona 
    INNER JOIN tb_usuario u ON u.id_persona = p.id_persona 
    INNER JOIN tb_cuentas_bancarias cs ON s.id_cuenta = cs.id_cuenta
    WHERE 
        s.id_grupo = id_grupo
        AND ((prestatario_nombre IS NULL OR prestatario_nombre = '') OR CONCAT(p.nombre, ' ', p.paterno, ' ', p.materno) LIKE CONCAT('%', prestatario_nombre, '%'))
        AND (estado IS NOT NULL AND estado != '')
        AND s.estado = estado
    ORDER BY s.fec_solicitud DESC; 
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE spListarPrestamos (IN p_id_usuario bigint)
BEGIN
    SELECT
        p.id_prestamo,
        CONCAT(per.nombre, ' ', per.paterno, ' ', per.materno) AS nombre_cliente,
        s.monto AS monto_prestamo,
        COUNT(c.id_cuota) AS numero_cuotas,
        CASE
            WHEN p.estado = 1 THEN 'En proceso'
            WHEN p.estado = 2 THEN 'Completado'
            ELSE 'Estado desconocido'
        END AS estado_prestamo
    FROM
        tb_prestamo p
        JOIN tb_solicitud s ON p.id_solicitud = s.id_solicitud
        JOIN tb_cliente cte ON s.id_cliente = cte.id_cliente
        JOIN tb_persona per ON cte.id_persona = per.id_persona
        LEFT JOIN tb_cuota c ON p.id_prestamo = c.id_prestamo
    WHERE
        EXISTS (
            SELECT 1
            FROM tb_prestamista pm
            WHERE pm.id_usuario = p_id_usuario AND pm.id_grupo = p.id_grupo
        )
    GROUP BY
        p.id_prestamo,
        nombre_cliente,
        monto_prestamo,
        estado_prestamo;
END //
DELIMITER ;

DELIMITER //

CREATE PROCEDURE actualizarMontoPagado (
    IN p_id_prestamo bigint,
    IN p_monto_ingresado double
)
BEGIN
    DECLARE monto_actual double;

    -- Obtener el monto_pagado actual
    SELECT monto_pagado INTO monto_actual
    FROM tb_prestamo
    WHERE id_prestamo = p_id_prestamo;

    -- Actualizar el monto_pagado sumando el valor ingresado
    UPDATE tb_prestamo
    SET monto_pagado = monto_actual + p_monto_ingresado
    WHERE id_prestamo = p_id_prestamo;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE actualizarMoraPagada (
    IN p_id_prestamo bigint,
    IN p_mora_ingresada double
)
BEGIN
    DECLARE mora_actual double;

    -- Obtener la mora_pagada actual
    SELECT mora_pagada INTO mora_actual
    FROM tb_prestamo
    WHERE id_prestamo = p_id_prestamo;

    -- Actualizar la mora_pagada sumando el valor ingresado
    UPDATE tb_prestamo
    SET mora_pagada = mora_actual + p_mora_ingresada
    WHERE id_prestamo = p_id_prestamo;
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE ObtenerCuotasXClienteID(id_cliente INT)
BEGIN
    SELECT CU.id_cuota,CU.numero,CU.monto,CU.monto_pagado,CU.fec_pago,CU.estado,CU.id_prestamo FROM tb_cuota as CU
    INNER JOIN tb_prestamo as PE ON PE.id_prestamo=CU.id_prestamo
    INNER JOIN tb_solicitud as SO ON PE.id_solicitud=SO.id_solicitud
    WHERE SO.id_cliente=id_cliente;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `ObtenerEstadistica`(id_jefe INT)
BEGIN
	SELECT CONCAT(PE.nombre,' ',PE.paterno) as 'prestamista',SUM(PO.monto) AS 'prestado',SUM(PO.monto_pagado) AS 'pagado',(SUM(PO.monto)+SUM(PO.interes_agregado)-SUM(PO.monto_pagado)) AS 'pendiente',SUM(PO.interes_agregado) as 'interes',SUM(PO.mora_pagada) as 'mora',((SUM(PO.interes_agregado)+SUM(PO.mora_pagada))/SUM(PO.monto)) AS 'rentabilidad' FROM tb_prestamo AS PO
	INNER JOIN tb_grupo AS G ON PO.id_grupo=G.id_grupo
	INNER JOIN tb_prestamista AS PA ON PA.id_prestamista=PO.id_prestamista
	INNER JOIN tb_usuario AS U ON U.id_usuario=PA.id_usuario
	INNER JOIN tb_persona AS PE ON PE.id_persona=U.id_persona
	WHERE G.id_jefe=id_jefe
	group by CONCAT(PE.nombre,' ',PE.paterno);
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `spListarPrestamosJefePrestamista`(IN p_id_usuario bigint)
BEGIN
    SELECT
        p.id_prestamo,
        CONCAT(per.nombre, ' ', per.paterno, ' ', per.materno) AS nombre_cliente,
        s.monto AS monto_prestamo,
        p.interes_agregado AS monto_interes,
        COUNT(c.id_cuota) AS numero_cuotas,
        CASE
            WHEN p.estado = 1 THEN 'En proceso'
            WHEN p.estado = 2 THEN 'Completado'
            ELSE 'Estado desconocido'
        END AS estado_prestamo
    FROM
        tb_prestamo p
        JOIN tb_solicitud s ON p.id_solicitud = s.id_solicitud
        JOIN tb_cliente cte ON s.id_cliente = cte.id_cliente
        JOIN tb_persona per ON cte.id_persona = per.id_persona
        LEFT JOIN tb_cuota c ON p.id_prestamo = c.id_prestamo
    WHERE
        EXISTS (
			SELECT 1
            FROM tb_grupo g
            WHERE g.id_jefe = p_id_usuario AND g.id_grupo = p.id_grupo
        )
    GROUP BY
        p.id_prestamo,
        nombre_cliente,
        monto_prestamo,
        estado_prestamo;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `ObtenerInformacionPrestamosJefePrestamista`(IN p_id_prestamo bigint)
BEGIN
    SELECT 
        c.id_prestamo,
        c.id_cuota,
        CONCAT(per.nombre, ' ', per.paterno, ' ', per.materno) AS nombre,
        c.numero,
        c.monto,
        c.monto_pagado,
        DATE_ADD(c.fec_pago, INTERVAL 1 DAY) AS fecha_vencimiento,
        c.estado
    FROM 
        tb_cuota c
    JOIN 
        tb_prestamo p ON c.id_prestamo = p.id_prestamo
    JOIN 
        tb_solicitud s ON p.id_solicitud = s.id_solicitud
    JOIN 
        tb_cliente cl ON s.id_cliente = cl.id_cliente
    JOIN 
        tb_persona per ON cl.id_persona = per.id_persona
    JOIN
        tb_prestamista pr ON p.id_prestamista = pr.id_prestamista
    WHERE
        p.id_prestamo = p_id_prestamo;
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE `FiltrarSolicitudes`(id_grupo int,nombre VARCHAR(45),estado int)
BEGIN
    SELECT s.id_solicitud, CONCAT(p.nombre, ' ', p.paterno, ' ', p.materno) as prestatario, s.monto, s.fec_solicitud, cs.banco, s.estado
    FROM tb_solicitud s 
    INNER JOIN tb_cliente c ON c.id_cliente = s.id_cliente 
    INNER JOIN tb_persona p ON c.id_persona = p.id_persona 
    INNER JOIN tb_usuario u ON u.id_persona = p.id_persona 
    INNER JOIN tb_cuentas_bancarias cs ON s.id_cuenta = cs.id_cuenta
    WHERE s.id_grupo = id_grupo AND p.nombre LIKE (CONCAT(nombre,'%')) AND s.estado=estado;
END//
DELIMITER ;