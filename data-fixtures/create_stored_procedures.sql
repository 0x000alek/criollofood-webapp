-- USUARIOS

CREATE OR REPLACE
PROCEDURE OBTENER_USUARIO_BY_USERNAME
(i_username IN CORE_USER.USERNAME%TYPE,
 o_user_id OUT CORE_USER.ID%TYPE,
 o_password OUT CORE_USER.PASSWORD%TYPE,
 o_first_name OUT CORE_USER.FIRST_NAME%TYPE,
 o_last_name OUT CORE_USER.LAST_NAME%TYPE,
 o_email OUT CORE_USER.EMAIL%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    select
        ID,
        PASSWORD,
        FIRST_NAME,
        LAST_NAME,
        EMAIL
    into
        o_user_id,
        o_password,
        o_first_name,
        o_last_name,
        o_email
    from CORE_USER
    where USERNAME = i_username;

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END OBTENER_USUARIO_BY_USERNAME;

CREATE OR REPLACE
PROCEDURE CREAR_USUARIO
(i_username IN CORE_USER.USERNAME%TYPE,
 i_password IN CORE_USER.PASSWORD%TYPE,
 i_first_name IN CORE_USER.FIRST_NAME%TYPE,
 i_last_name IN CORE_USER.LAST_NAME%TYPE,
 i_email IN CORE_USER.EMAIL%TYPE,
 i_is_superuser IN CORE_USER.IS_SUPERUSER%TYPE,
 i_is_staff IN CORE_USER.IS_STAFF%TYPE,
 i_grupo_id IN AUTH_GROUP.ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_date_joined TIMESTAMP;
    v_usuario_id NUMBER;
BEGIN
    v_date_joined := CURRENT_TIMESTAMP;

    insert into CORE_USER (ID, PASSWORD, LAST_LOGIN, IS_SUPERUSER, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, IS_STAFF, IS_ACTIVE, DATE_JOINED)
    values (v_usuario_id, i_password, CURRENT_TIMESTAMP, i_is_superuser, i_username, i_first_name, i_last_name, i_email, i_is_staff, 1, v_date_joined);

    select ID into v_usuario_id from CORE_USER where DATE_JOINED = v_date_joined;

    insert into CORE_USER_GROUPS (USER_ID, GROUP_ID) values (v_usuario_id, i_grupo_id);

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_USUARIO;

CREATE OR REPLACE
PROCEDURE LOGIN_USUARIO
(i_usuario_id IN CORE_USER.ID%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    update CORE_USER
        set LAST_LOGIN = CURRENT_TIMESTAMP
    where ID = i_usuario_id;

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END LOGIN_USUARIO;

-- GRUPOS

CREATE OR REPLACE
PROCEDURE FIND_ALL_GRUPOS
(o_grupos_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_grupos_cursor for
        select * from GRUPOS;
END FIND_ALL_GRUPOS;

CREATE OR REPLACE
PROCEDURE LISTAR_GRUPOS_BY_ID_USUARIO
(i_usuario_id IN CORE_USER.ID%TYPE,
 o_grupos_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_grupos_cursor for
        select
            grupos.ID   as id,
            grupos.NAME as name
        from CORE_USER_GROUPS usrgrp join AUTH_GROUP grupos
        on usrgrp.GROUP_ID = grupos.ID
        where usrgrp.USER_ID = i_usuario_id;
END LISTAR_GRUPOS_BY_ID_USUARIO;

-- PERMISOS

CREATE OR REPLACE
PROCEDURE FIND_PERMISOS_BY_ID_GRUPO
(i_grupo_id IN GRUPOS.ID%TYPE,
 o_permisos_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_permisos_cursor for
        select
            prmsos.id   as id,
            prmsos.NAME as name
        from GRUPO_PERMISOS grppms
        join PERMISOS prmsos on grppms.PERMISO_ID = prmsos.ID
        where grppms.GRUPO_ID = i_grupo_id;
END FIND_PERMISOS_BY_ID_GRUPO;

-- CLIENTES

CREATE OR REPLACE
PROCEDURE OBTENER_CLIENTE_BY_CORREO
(i_correo IN CORE_CLIENTE.CORREO%TYPE,
 o_id OUT CORE_CLIENTE.ID%TYPE,
 o_nombre OUT CORE_CLIENTE.NOMBRE%TYPE,
 o_telefono OUT CORE_CLIENTE.TELEFONO%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    select
        ID,
        NOMBRE,
        TELEFONO
    into
        o_id,
        o_nombre,
        o_telefono
    from CORE_CLIENTE
    where CORREO = i_correo;

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END OBTENER_CLIENTE_BY_CORREO;

CREATE OR REPLACE
PROCEDURE CREAR_CLIENTE
(i_nombre IN CORE_CLIENTE.NOMBRE%TYPE,
 i_telefono IN CORE_CLIENTE.TELEFONO%TYPE,
 i_correo IN CORE_CLIENTE.CORREO%TYPE,
 o_id OUT CORE_CLIENTE.ID%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    insert into CORE_CLIENTE (NOMBRE, TELEFONO, CORREO)
    values (i_nombre, i_telefono, i_correo);

    select ID into o_id
    from CORE_CLIENTE
    where CORREO = i_correo;

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_CLIENTE;

-- RESERVACIONES

CREATE OR REPLACE
PROCEDURE CREAR_RESERVACION
(i_fecha IN CORE_RESERVACION.FECHA%TYPE,
 i_asistentes IN CORE_RESERVACION.ASISTENTES%TYPE,
 i_cliente_id IN CORE_RESERVACION.CLIENTE_ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_id NUMBER;
    v_codigo_reservacion VARCHAR2(15);
    v_fecha_confirmacion TIMESTAMP;
BEGIN
    v_fecha_confirmacion := CURRENT_TIMESTAMP;

    insert into CORE_RESERVACION (FECHA, ASISTENTES, ESTADO, CLIENTE_ID, FECHA_CONFIRMACION)
    values (i_fecha, i_asistentes, 'CONFIRMADA', i_cliente_id, v_fecha_confirmacion);

    select ID into v_id from CORE_RESERVACION where FECHA_CONFIRMACION = v_fecha_confirmacion;

    v_codigo_reservacion := 'RSV-' || LPAD(TO_CHAR(v_id), 11, '0');

    update CORE_RESERVACION
    set CODIGO = v_codigo_reservacion
    where ID = v_id;

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_RESERVACION;

CREATE OR REPLACE
PROCEDURE LISTAR_RESERVACIONES
(o_reservaciones_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_reservaciones_cursor for
        select
            reserv.ID AS ID,
            reserv.FECHA AS FECHA,
            reserv.ASISTENTES AS ASISTENTES,
            reserv.CODIGO AS CODIGO,
            reserv.ESTADO AS ESTADO,
            cliente.NOMBRE AS NOMBRE_CLIENTE
        from CORE_RESERVACION reserv join CORE_CLIENTE cliente
        on reserv.CLIENTE_ID = cliente.ID
        order by ID;
END LISTAR_RESERVACIONES;

CREATE OR REPLACE
PROCEDURE LISTAR_RESERVACIONES_BY_FECHA
(i_fecha IN CORE_RESERVACION.FECHA%TYPE,
 o_reservaciones_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_reservaciones_cursor for
        select
            reserv.ID AS ID,
            reserv.FECHA AS FECHA,
            reserv.ASISTENTES AS ASISTENTES,
            reserv.CODIGO AS CODIGO,
            reserv.ESTADO AS ESTADO,
            cliente.NOMBRE AS NOMBRE_CLIENTE
        from CORE_RESERVACION reserv join CORE_CLIENTE cliente
        on reserv.CLIENTE_ID = cliente.ID
        where trunc(FECHA, 'DDD') = trunc(i_fecha, 'DDD')
        order by ID;
END LISTAR_RESERVACIONES_BY_FECHA;

CREATE OR REPLACE
PROCEDURE LISTAR_RESERVACIONES_BY_ID_CLIENTE
(i_cliente_id IN CORE_RESERVACION.CLIENTE_ID%TYPE,
 o_reservaciones_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_reservaciones_cursor for
        select
            ID,
            FECHA,
            ASISTENTES,
            CODIGO,
            ESTADO
        from CORE_RESERVACION
        where CLIENTE_ID = i_cliente_id
        order by FECHA desc;
END LISTAR_RESERVACIONES_BY_ID_CLIENTE;

CREATE OR REPLACE
PROCEDURE CAMBIAR_ESTADO_RESERVACION
(i_id IN CORE_RESERVACION.ID%TYPE,
 i_estado IN CORE_RESERVACION.ESTADO%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN

    update CORE_RESERVACION
    set ESTADO = i_estado
    where ID = i_id;

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CAMBIAR_ESTADO_RESERVACION;

-- RECETAS

CREATE OR REPLACE
PROCEDURE LISTAR_RECETAS_DISPONIBLES
(o_recetas_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_recetas_cursor for
        select * from PRODUCTOS_RECETA where ESTA_DISPONIBLE = 1;
END LISTAR_RECETAS_DISPONIBLES;

-- ATENCION

CREATE OR REPLACE
PROCEDURE CREAR_ATENCION
(i_reservacion_id IN CORE_CLIENTE.ID%TYPE,
 i_mesa_id IN CORE_MESA.NUMERO_MESA%TYPE,
 o_atencion_id OUT ATENCION_ATENCION.ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_cliente_id NUMBER(11);
    v_id_atencion NUMBER(11);
    v_fecha TIMESTAMP;
    v_codigo VARCHAR(150);
BEGIN
    v_fecha := CURRENT_TIMESTAMP;

    select CLIENTE_ID into v_cliente_id from CORE_RESERVACION where ID = i_reservacion_id;

    insert into ATENCION_ATENCION (FECHA, ESTA_ACTIVO, ESTA_PAGADA, CLIENTE_ID, NUMERO_MESA)
    values (v_fecha, 1, 0, v_cliente_id, i_mesa_id);

    select ID into v_id_atencion FROM ATENCION_ATENCION where FECHA = v_fecha;

    v_codigo := 'ATN-' || LPAD(TO_CHAR(v_id_atencion), 11, '0');

    update ATENCION_ATENCION
        set CODIGO = v_codigo
    where FECHA = v_fecha;

    update CORE_MESA
        set EN_USO = 1
    where NUMERO_MESA = i_mesa_id;

    update CORE_RESERVACION
        set ESTADO = 'TOMADA'
    where ID = i_reservacion_id;

    o_atencion_id := v_id_atencion;
    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_ATENCION;

CREATE OR REPLACE
PROCEDURE OBTENER_ATENCION_BY_ID_CLIENTE
(i_cliente_id IN CORE_CLIENTE.ID%TYPE,
 o_id OUT ATENCION_ATENCION.ID%TYPE,
 o_numero_mesa OUT ATENCION_ATENCION.NUMERO_MESA%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    select
        ID, NUMERO_MESA
    into
        o_id, o_numero_mesa
    from ATENCION_ATENCION
    where CLIENTE_ID = i_cliente_id;

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END OBTENER_ATENCION_BY_ID_CLIENTE;

-- PEDIDO

CREATE OR REPLACE
PROCEDURE CREAR_PEDIDO
(i_atencion_id IN ATENCION_ATENCION.ID%TYPE,
 o_pedido_id OUT ATENCION_PEDIDO.ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_fecha_ingreso TIMESTAMP;
    v_pedido_id NUMBER(11);
BEGIN
    v_fecha_ingreso := CURRENT_TIMESTAMP;

    insert into ATENCION_PEDIDO (ESTADO, FECHA_INGRESO, ATENCION_ID)
    values ('INGRESADO', v_fecha_ingreso, i_atencion_id);

    select ID into v_pedido_id from ATENCION_PEDIDO where FECHA_INGRESO = v_fecha_ingreso;

    o_pedido_id := v_pedido_id;
    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_PEDIDO;

CREATE OR REPLACE
PROCEDURE AGREGAR_AL_PEDIDO
(i_pedido_id IN ATENCION_PEDIDO.ID%TYPE,
 i_receta_id IN PRODUCTOS_RECETA.ID%TYPE,
 i_cantidad IN NUMBER,
 o_sql_code OUT NUMBER) AS
BEGIN
    insert into ATENCION_PEDIDO_RECETA (PEDIDO_ID, RECETA_ID, CANTIDAD)
    values (i_pedido_id, i_receta_id, i_cantidad);

    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END AGREGAR_AL_PEDIDO;

CREATE OR REPLACE
PROCEDURE LISTAR_PEDIDOS_PENDIENTES
(o_pedidos_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_pedidos_cursor for
        select
               t1.PEDIDO_ID as PEDIDO_ID,
               t1.RECETA_ID as RECETA_ID,
               t2.NOMBRE as NOMBRE_RECETA,
               t1.CANTIDAD as CANTIDAD_RECETA
        from ATENCION_PEDIDO t0 join ATENCION_PEDIDO_RECETA t1
        on t0.ID = t1.PEDIDO_ID
        join PRODUCTOS_RECETA t2
        on t1.RECETA_ID = t2.ID
        where t0.ESTADO <> 'DESPACHADO';
END LISTAR_PEDIDOS_PENDIENTES;

-- CATEGORIA

CREATE OR REPLACE
PROCEDURE LISTAR_CATEGORIAS
(o_categorias_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_categorias_cursor for
        select * from PRODUCTOS_CATEGORIA;
END LISTAR_CATEGORIAS;

-- MESA

CREATE OR REPLACE
PROCEDURE LISTAR_MESAS
(o_mesas_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_mesas_cursor for
        select * from CORE_MESA;
END LISTAR_MESAS;


COMMIT;
