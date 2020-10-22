CREATE OR REPLACE
PROCEDURE FIND_USUARIO_BY_USERNAME
(i_username IN USUARIOS.username%TYPE,
 o_user_id OUT USUARIOS.id%TYPE,
 o_username OUT USUARIOS.username%TYPE,
 o_password OUT USUARIOS.password%TYPE,
 o_first_name OUT USUARIOS.first_name%TYPE,
 o_last_name OUT USUARIOS.last_name%TYPE,
 o_email OUT USUARIOS.email%TYPE,
 o_role_id OUT ROLES.id%TYPE,
 o_role_name OUT ROLES.name%TYPE) AS
BEGIN
    select
        u.id,
        u.username,
        u.password,
        u.first_name,
        u.last_name,
        u.email,
        r.id,
        r.name
    into
        o_user_id,
        o_username,
        o_password,
        o_first_name,
        o_last_name,
        o_email,
        o_role_id,
        o_role_name
    from USUARIOS u join ROLES r
    on u.role_id = r.id
    where u.username = i_username;
END FIND_USUARIO_BY_USERNAME;

CREATE OR REPLACE
PROCEDURE CREATE_USUARIO
(i_username IN USUARIOS.username%TYPE,
 i_password IN USUARIOS.password%TYPE,
 i_first_name IN USUARIOS.first_name%TYPE,
 i_last_name IN USUARIOS.last_name%TYPE,
 i_email IN USUARIOS.email%TYPE,
 i_grupo_id IN GRUPOS.id%TYPE,
 i_is_superuser IN USUARIOS.is_superuser%TYPE,
 i_is_staff IN USUARIOS.is_staff%TYPE,
 o_sql_code OUT NUMBER) AS
BEGIN
    insert into usuarios (id, username, password, first_name, last_name, email, is_active, grupo_id, last_login, is_superuser, is_staff, date_joined)
    values (usuarios_seq.nextval, i_username, i_password, i_first_name, i_last_name, i_email, 1, i_grupo_id, CURRENT_TIMESTAMP, i_is_superuser, i_is_staff, CURRENT_TIMESTAMP);

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

COMMIT;
