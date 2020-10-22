CREATE TABLE grupos (
    id    NUMBER NOT NULL,
    name  VARCHAR2(150)
);

ALTER TABLE grupos ADD CONSTRAINT grupos_pk PRIMARY KEY ( id );

CREATE TABLE grupo_permisos (
    grupo_id    NUMBER NOT NULL,
    permiso_id  NUMBER NOT NULL
);

ALTER TABLE grupo_permisos ADD CONSTRAINT grupo_permisos_pk PRIMARY KEY ( grupo_id,
                                                                          permiso_id );

CREATE TABLE permisos (
    id      NUMBER NOT NULL,
    name    VARCHAR2(255)
);

ALTER TABLE permisos ADD CONSTRAINT permisos_pk PRIMARY KEY ( id );

CREATE TABLE usuarios (
    id              NUMBER NOT NULL,
    username        VARCHAR2(150) NOT NULL,
    password        VARCHAR2(128) NOT NULL,
    first_name      VARCHAR2(150),
    last_name       VARCHAR2(150),
    email           VARCHAR2(254) NOT NULL,
    is_active       NUMBER NOT NULL,
    grupo_id        NUMBER NOT NULL,
    last_login      TIMESTAMP,
    is_superuser    NUMBER NOT NULL,
    is_staff        NUMBER NOT NULL,
    date_joined     TIMESTAMP NOT NULL
);

ALTER TABLE usuarios ADD CONSTRAINT usuarios_pk PRIMARY KEY ( id );

ALTER TABLE grupo_permisos
    ADD CONSTRAINT grupo_permisos_grupo_fk FOREIGN KEY ( grupo_id )
        REFERENCES grupos ( id );

ALTER TABLE grupo_permisos
    ADD CONSTRAINT grupo_permisos_permiso_fk FOREIGN KEY ( permiso_id )
        REFERENCES permisos ( id );

ALTER TABLE usuarios
    ADD CONSTRAINT usuario_grupo_fk FOREIGN KEY ( grupo_id )
        REFERENCES grupos ( id );
