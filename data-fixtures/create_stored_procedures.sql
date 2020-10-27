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


COMMIT;
