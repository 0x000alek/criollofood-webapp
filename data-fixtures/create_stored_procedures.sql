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
PROCEDURE FIND_CLIENTE_BY_CORREO
(i_correo IN CLIENTES.correo%TYPE,
 o_id OUT CLIENTES.id%TYPE,
 o_nombre OUT CLIENTES.nombre%TYPE,
 o_telefono OUT CLIENTES.telefono%TYPE) AS
BEGIN
    select
        id,
        nombre,
        telefono
    into
        o_id,
        o_nombre,
        o_telefono
    from CLIENTES
    where correo = i_correo;
END FIND_CLIENTE_BY_CORREO;

CREATE OR REPLACE
PROCEDURE CREATE_CLIENTE
(i_nombre IN CLIENTES.nombre%TYPE,
 i_telefono IN CLIENTES.telefono%TYPE,
 i_correo IN CLIENTES.correo%TYPE,
 o_sql_code OUT NUMBER) AS
    v_cliente_id NUMBER;
BEGIN
    v_cliente_id := CLIENTES_SEQ.nextval;

    insert into CLIENTES (ID, NOMBRE, TELEFONO, CORREO) values (v_cliente_id, i_nombre, i_telefono, i_correo);

    o_sql_code := 0;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 1;
END CREATE_CLIENTE;

CREATE OR REPLACE
PROCEDURE VALIDATE_CLIENTE_EXISTS
(i_correo IN CLIENTES.correo%TYPE,
 o_sql_code OUT NUMBER) AS
    v_cliente_id NUMBER;
BEGIN
    o_sql_code := 0;
    select id into v_cliente_id from CLIENTES where correo = i_correo;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        o_sql_code := 1;
END VALIDATE_CLIENTE_EXISTS;

-- RESERVACIOONES

CREATE OR REPLACE
PROCEDURE CREATE_RESERVACION
(i_fecha IN RESERVACIONES.FECHA%TYPE,
 i_asistentes IN RESERVACIONES.ASISTENTES%TYPE,
 i_cliente_id IN RESERVACIONES.CLIENTE_ID%TYPE,
 o_sql_code OUT NUMBER) AS
    v_id NUMBER;
    v_codigo_reservacion VARCHAR2(10);
BEGIN
    v_id := RESERVACIONES_SEQ.nextval;
    v_codigo_reservacion := 'RSV-' || LPAD(TO_CHAR(v_id), 6, '0');

    insert into RESERVACIONES (ID, FECHA, ASISTENTES, CODIGO, ESTADO, CLIENTE_ID)
    values (v_id, i_fecha, i_asistentes, v_codigo_reservacion, 'CONFIRMADA', i_cliente_id);

    o_sql_code := 0;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 1;
END CREATE_RESERVACION;

CREATE OR REPLACE
PROCEDURE FIND_RESERVACIONES_BY_ID_CLIENTE
(i_cliente_id IN RESERVACIONES.CLIENTE_ID%TYPE,
 o_reservaciones_cursor OUT SYS_REFCURSOR) AS
BEGIN
    open o_reservaciones_cursor for
        select
            id,
            fecha,
            asistentes,
            codigo,
            estado
        from RESERVACIONES
        where cliente_id = i_cliente_id
        order by fecha desc;
END FIND_RESERVACIONES_BY_ID_CLIENTE;

CREATE OR REPLACE
PROCEDURE DELETE_RESERVACION
(i_id IN RESERVACIONES.ID%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN

    update RESERVACIONES
        set estado = 'CANCELADA'
    where id = i_id;

    o_sql_code := 0;

    commit;
EXCEPTION
    WHEN OTHERS THEN
        o_sql_code := 1;
END DELETE_RESERVACION;


COMMIT;
