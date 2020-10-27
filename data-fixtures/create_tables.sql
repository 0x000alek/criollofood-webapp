CREATE TABLE atenciones (
    id           NUMBER NOT NULL,
    codigo       VARCHAR2(150) NOT NULL,
    total        INTEGER NOT NULL,
    fecha        DATE NOT NULL,
    esta_activa  NUMBER NOT NULL,
    numero_mesa  INTEGER NOT NULL,
    cliente_id   NUMBER NOT NULL,
    esta_pagada  NUMBER NOT NULL
);

ALTER TABLE atenciones ADD CONSTRAINT atenciones_pk PRIMARY KEY ( id );

CREATE TABLE boletas (
    id             NUMBER NOT NULL,
    codigo_sii     VARCHAR2(50) NOT NULL,
    fecha_emision  DATE NOT NULL,
    total          INTEGER NOT NULL,
    pago_id        NUMBER NOT NULL
);

CREATE UNIQUE INDEX boletas__idx ON
    boletas (
        pago_id
    ASC );

ALTER TABLE boletas ADD CONSTRAINT boletas_pk PRIMARY KEY ( id );

CREATE TABLE cajas (
    numero_caja   INTEGER NOT NULL,
    esta_abierta  NUMBER NOT NULL,
    cajero        NUMBER NOT NULL
);

ALTER TABLE cajas ADD CONSTRAINT cajas_pk PRIMARY KEY ( numero_caja );

CREATE TABLE categorias (
    id             NUMBER NOT NULL,
    nombre         VARCHAR2(100) NOT NULL,
    esta_en_carta  NUMBER NOT NULL
);

ALTER TABLE categorias ADD CONSTRAINT categorias_pk PRIMARY KEY ( id );

CREATE TABLE clientes (
    id        NUMBER NOT NULL,
    nombre    VARCHAR2(100) NOT NULL,
    telefono  INTEGER,
    correo    VARCHAR2(255)
);

ALTER TABLE clientes ADD CONSTRAINT clientes_pk PRIMARY KEY ( id );

CREATE TABLE grupo_permisos (
    grupo_id    NUMBER NOT NULL,
    permiso_id  NUMBER NOT NULL
);

ALTER TABLE grupo_permisos ADD CONSTRAINT grupo_permisos_pk PRIMARY KEY ( grupo_id,
                                                                          permiso_id );

CREATE TABLE grupos (
    id    NUMBER NOT NULL,
    name  VARCHAR2(150)
);

ALTER TABLE grupos ADD CONSTRAINT grupos_pk PRIMARY KEY ( id );

CREATE TABLE medios_pago (
    id           NUMBER NOT NULL,
    nombre       VARCHAR2(100) NOT NULL,
    esta_activo  NUMBER NOT NULL
);

ALTER TABLE medios_pago ADD CONSTRAINT medios_pago_pk PRIMARY KEY ( id );

CREATE TABLE mesas (
    numero_mesa  INTEGER NOT NULL,
    en_uso       NUMBER NOT NULL,
    asientos     INTEGER NOT NULL
);

ALTER TABLE mesas ADD CONSTRAINT mesas_pk PRIMARY KEY ( numero_mesa );

CREATE TABLE pagos (
    id             NUMBER NOT NULL,
    monto          NUMBER NOT NULL,
    fecha          DATE NOT NULL,
    medio_pago_id  NUMBER NOT NULL,
    numero_caja    INTEGER,
    atencion_id    NUMBER NOT NULL
);

ALTER TABLE pagos ADD CONSTRAINT pagos_pk PRIMARY KEY ( id );

CREATE TABLE pedido_productos (
    pedido_id    NUMBER NOT NULL,
    producto_id  NUMBER NOT NULL
);

ALTER TABLE pedido_productos ADD CONSTRAINT pedido_productos_pk PRIMARY KEY ( pedido_id,
                                                                              producto_id );

CREATE TABLE pedidos (
    id                 NUMBER NOT NULL,
    garzon             NUMBER NOT NULL,
    estado             VARCHAR2(20) NOT NULL,
    fecha_ingreso      DATE NOT NULL,
    fecha_preparacion  DATE,
    fecha_entrega      DATE,
    atencion_id        NUMBER NOT NULL
);

ALTER TABLE pedidos ADD CONSTRAINT pedidos_pk PRIMARY KEY ( id );

CREATE TABLE permisos (
    id    NUMBER NOT NULL,
    name  VARCHAR2(255)
);

ALTER TABLE permisos ADD CONSTRAINT permisos_pk PRIMARY KEY ( id );

CREATE TABLE productos (
    id                   NUMBER NOT NULL,
    nombre               VARCHAR2(150) NOT NULL,
    cantidad_stock       INTEGER NOT NULL,
    fecha_ingreso        DATE NOT NULL,
    es_venta_individual  NUMBER NOT NULL,
    punto_pedido         INTEGER NOT NULL,
    stock_minimo         INTEGER NOT NULL,
    stock_maximo         INTEGER NOT NULL,
    precio_venta         INTEGER NOT NULL,
    precio_compra        INTEGER NOT NULL,
    categoria_id         NUMBER NOT NULL,
    unidad_medida_id     NUMBER NOT NULL,
    imagen               BLOB
);

ALTER TABLE productos ADD CONSTRAINT productos_pk PRIMARY KEY ( id );

CREATE TABLE receta_productos (
    producto_id  NUMBER NOT NULL,
    receta_id    NUMBER NOT NULL
);

ALTER TABLE receta_productos ADD CONSTRAINT receta_productos_pk PRIMARY KEY ( producto_id,
                                                                              receta_id );

CREATE TABLE recetas (
    id               NUMBER NOT NULL,
    nombre           VARCHAR2(100) NOT NULL,
    descripcion      VARCHAR2(255) NOT NULL,
    fecha_ingreso    DATE NOT NULL,
    precio           INTEGER NOT NULL,
    esta_disponible  NUMBER NOT NULL,
    categoria_id     NUMBER NOT NULL,
    imagen           BLOB
);

ALTER TABLE recetas ADD CONSTRAINT recetas_pk PRIMARY KEY ( id );

CREATE TABLE unidades_medida (
    id           NUMBER NOT NULL,
    nombre       VARCHAR2(50) NOT NULL,
    abreviatura  VARCHAR2(5) NOT NULL
);

ALTER TABLE unidades_medida ADD CONSTRAINT unidades_medida_pk PRIMARY KEY ( id );

CREATE TABLE usuario_grupo (
    usuarios_id  NUMBER NOT NULL,
    grupos_id    NUMBER NOT NULL
);

ALTER TABLE usuario_grupo ADD CONSTRAINT usuario_grupos_pk PRIMARY KEY ( usuarios_id,
                                                                         grupos_id );

CREATE TABLE usuario_permisos (
    usuario_id  NUMBER NOT NULL,
    permiso_id  NUMBER NOT NULL
);

ALTER TABLE usuario_permisos ADD CONSTRAINT usuario_permiso_pk PRIMARY KEY ( usuario_id,
                                                                             permiso_id );

CREATE TABLE usuarios (
    id            NUMBER NOT NULL,
    password      VARCHAR2(128),
    last_login    TIMESTAMP,
    is_superuser  NUMBER NOT NULL,
    username      VARCHAR2(150),
    first_name    VARCHAR2(150),
    last_name     VARCHAR2(150),
    email         VARCHAR2(254),
    is_staff      NUMBER NOT NULL,
    is_active     NUMBER NOT NULL,
    date_joined   TIMESTAMP NOT NULL
);

ALTER TABLE usuarios ADD CONSTRAINT usuarios_pk PRIMARY KEY ( id );

ALTER TABLE atenciones
    ADD CONSTRAINT atenciones_clientes_fk FOREIGN KEY ( cliente_id )
        REFERENCES clientes ( id );

ALTER TABLE atenciones
    ADD CONSTRAINT atenciones_mesas_fk FOREIGN KEY ( numero_mesa )
        REFERENCES mesas ( numero_mesa );

ALTER TABLE boletas
    ADD CONSTRAINT boletas_pagos_fk FOREIGN KEY ( pago_id )
        REFERENCES pagos ( id );

ALTER TABLE cajas
    ADD CONSTRAINT cajas_usuarios_fk FOREIGN KEY ( cajero )
        REFERENCES usuarios ( id );

ALTER TABLE grupo_permisos
    ADD CONSTRAINT grupo_permisos_grupo_fk FOREIGN KEY ( grupo_id )
        REFERENCES grupos ( id );

ALTER TABLE grupo_permisos
    ADD CONSTRAINT grupo_permisos_permiso_fk FOREIGN KEY ( permiso_id )
        REFERENCES permisos ( id );

ALTER TABLE pagos
    ADD CONSTRAINT pagos_atenciones_fk FOREIGN KEY ( atencion_id )
        REFERENCES atenciones ( id );

ALTER TABLE pagos
    ADD CONSTRAINT pagos_cajas_fk FOREIGN KEY ( numero_caja )
        REFERENCES cajas ( numero_caja );

ALTER TABLE pagos
    ADD CONSTRAINT pagos_medios_pago_fk FOREIGN KEY ( medio_pago_id )
        REFERENCES medios_pago ( id );

ALTER TABLE pedido_productos
    ADD CONSTRAINT pedido_productos_pedido_fk FOREIGN KEY ( pedido_id )
        REFERENCES pedidos ( id );

ALTER TABLE pedido_productos
    ADD CONSTRAINT pedido_productos_producto_fk FOREIGN KEY ( producto_id )
        REFERENCES productos ( id );

ALTER TABLE pedidos
    ADD CONSTRAINT pedidos_atenciones_fk FOREIGN KEY ( atencion_id )
        REFERENCES atenciones ( id );

ALTER TABLE pedidos
    ADD CONSTRAINT pedidos_usuarios_fk FOREIGN KEY ( garzon )
        REFERENCES usuarios ( id );

ALTER TABLE productos
    ADD CONSTRAINT productos_categorias_fk FOREIGN KEY ( categoria_id )
        REFERENCES categorias ( id );

ALTER TABLE productos
    ADD CONSTRAINT productos_unidades_medida_fk FOREIGN KEY ( unidad_medida_id )
        REFERENCES unidades_medida ( id );

ALTER TABLE receta_productos
    ADD CONSTRAINT receta_productos_producto_fk FOREIGN KEY ( producto_id )
        REFERENCES productos ( id );

ALTER TABLE receta_productos
    ADD CONSTRAINT receta_productos_receta_fk FOREIGN KEY ( receta_id )
        REFERENCES recetas ( id );

ALTER TABLE recetas
    ADD CONSTRAINT recetas_categorias_fk FOREIGN KEY ( categoria_id )
        REFERENCES categorias ( id );

ALTER TABLE usuario_grupo
    ADD CONSTRAINT usuario_grupos_grupo_fk FOREIGN KEY ( grupos_id )
        REFERENCES grupos ( id );

ALTER TABLE usuario_grupo
    ADD CONSTRAINT usuario_grupos_usuario_fk FOREIGN KEY ( usuarios_id )
        REFERENCES usuarios ( id );

ALTER TABLE usuario_permisos
    ADD CONSTRAINT usuario_permisos_permiso_fk FOREIGN KEY ( permiso_id )
        REFERENCES permisos ( id );

ALTER TABLE usuario_permisos
    ADD CONSTRAINT usuario_permisos_usuario_fk FOREIGN KEY ( usuario_id )
        REFERENCES usuarios ( id );
