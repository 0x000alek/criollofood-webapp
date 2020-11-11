-- USUARIOS

CREATE OR REPLACE
PROCEDURE FIND_ALL_USUARIOS
(o_usuarios_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_usuarios_cursor for
        select * from USUARIOS;
END FIND_ALL_USUARIOS;

CREATE OR REPLACE
PROCEDURE FIND_USUARIO_BY_USERNAME
(i_username IN USUARIOS.username%TYPE,
 o_user_id OUT USUARIOS.id%TYPE,
 o_password OUT USUARIOS.password%TYPE,
 o_first_name OUT USUARIOS.first_name%TYPE,
 o_last_name OUT USUARIOS.last_name%TYPE,
 o_email OUT USUARIOS.email%TYPE) AS
BEGIN
    select
        id,
        password,
        first_name,
        last_name,
        email
    into
        o_user_id,
        o_password,
        o_first_name,
        o_last_name,
        o_email
    from USUARIOS
    where username = i_username;
END FIND_USUARIO_BY_USERNAME;

CREATE OR REPLACE
PROCEDURE CREATE_USUARIO
(i_username IN USUARIOS.username%TYPE,
 i_password IN USUARIOS.password%TYPE,
 i_first_name IN USUARIOS.first_name%TYPE,
 i_last_name IN USUARIOS.last_name%TYPE,
 i_email IN USUARIOS.email%TYPE,
 i_is_superuser IN USUARIOS.is_superuser%TYPE,
 i_is_staff IN USUARIOS.is_staff%TYPE,
 i_grupo_id IN GRUPOS.ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_usuario_id NUMBER;
BEGIN
    v_usuario_id := USUARIOS_SEQ.nextval;

    insert into USUARIOS (ID, PASSWORD, LAST_LOGIN, IS_SUPERUSER, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, IS_STAFF, IS_ACTIVE, DATE_JOINED)
    values (v_usuario_id, i_password, CURRENT_TIMESTAMP, i_is_superuser, i_username, i_first_name, i_last_name, i_email, i_is_staff, 1, CURRENT_TIMESTAMP);

    insert into USUARIO_GRUPO (USUARIOS_ID, GRUPOS_ID) values (v_usuario_id, i_grupo_id);

    o_sql_code := 0;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 1;
END CREATE_USUARIO;

CREATE OR REPLACE
PROCEDURE VALIDATE_USUARIO_EXISTS
(i_username IN USUARIOS.username%TYPE,
 o_sql_code OUT NUMBER) AS
    v_user_id NUMBER;
BEGIN
    o_sql_code := 0;
    select id into v_user_id from USUARIOS where username = i_username;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        o_sql_code := 1;
END VALIDATE_USUARIO_EXISTS;

-- GRUPOS

CREATE OR REPLACE
PROCEDURE FIND_ALL_GRUPOS
(o_grupos_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_grupos_cursor for
        select * from GRUPOS;
END FIND_ALL_GRUPOS;

CREATE OR REPLACE
PROCEDURE FIND_GRUPOS_BY_ID_USUARIO
(i_usuario_id IN USUARIOS.ID%TYPE,
 o_grupos_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_grupos_cursor for
        select
            grupos.ID   as id,
            grupos.NAME as name
        from USUARIO_GRUPO usrgrp
                 join GRUPOS grupos on usrgrp.GRUPOS_ID = grupos.ID
        where usrgrp.USUARIOS_ID = i_usuario_id;
END FIND_GRUPOS_BY_ID_USUARIO;

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
 o_id OUT CORE_CLIENTE.id%TYPE,
 o_nombre OUT CORE_CLIENTE.nombre%TYPE,
 o_telefono OUT CORE_CLIENTE.telefono%TYPE) AS
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
END OBTENER_CLIENTE_BY_CORREO;

CREATE OR REPLACE
PROCEDURE CREAR_CLIENTE
(i_nombre IN CORE_CLIENTE.nombre%TYPE,
 i_telefono IN CORE_CLIENTE.telefono%TYPE,
 i_correo IN CORE_CLIENTE.correo%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    insert into CORE_CLIENTE (NOMBRE, TELEFONO, CORREO)
    values (i_nombre, i_telefono, i_correo);

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_CLIENTE;

CREATE OR REPLACE
PROCEDURE IS_CLIENTE_EXISTS
(i_correo IN CORE_CLIENTE.correo%TYPE,
 o_sql_code OUT NUMBER) AS
    v_cliente_id NUMBER;
BEGIN
    select ID into v_cliente_id from CORE_CLIENTE where CORREO = i_correo;
    o_sql_code := 1;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        o_sql_code := 0;
END IS_CLIENTE_EXISTS;

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
PROCEDURE CANCELAR_RESERVACION
(i_id IN CORE_RESERVACION.ID%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN

    update CORE_RESERVACION
    set ESTADO = 'CANCELADA'
    where ID = i_id;

    o_sql_code := 1;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CANCELAR_RESERVACION;

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
(i_cliente_id IN CORE_CLIENTE.ID%TYPE,
 i_mesa_id IN CORE_MESA.NUMERO_MESA%TYPE,
 o_atencion_id OUT ATENCION_ATENCION.ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_id_atencion NUMBER(11);
    v_fecha TIMESTAMP;
    v_codigo VARCHAR(150);
BEGIN
    v_fecha := CURRENT_TIMESTAMP;

    insert into ATENCION_ATENCION (FECHA, ESTA_ACTIVO, ESTA_PAGADA, CLIENTE_ID, NUMERO_MESA)
    values (v_fecha, 1, 0, i_cliente_id, i_mesa_id);

    select ID into v_id_atencion FROM ATENCION_ATENCION where FECHA = v_fecha;

    v_codigo := 'ATN-' || LPAD(TO_CHAR(v_id_atencion), 11, '0');

    update ATENCION_ATENCION
        set CODIGO = v_codigo
    where FECHA = v_fecha;

    o_atencion_id := v_id_atencion;
    o_sql_code := 1;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 0;
END CREAR_ATENCION;

CREATE OR REPLACE
PROCEDURE OBTENER_ATENCION_ACTIVA_CLIENTE
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
END OBTENER_ATENCION_ACTIVA_CLIENTE;

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


COMMIT;
