insert into AUTH_GROUP (NAME) values ('COCINA');
insert into AUTH_GROUP (NAME) values ('ADMINISTRADOR');
insert into AUTH_GROUP (NAME) values ('BODEGA');
insert into AUTH_GROUP (NAME) values ('FINANZAS');
insert into AUTH_GROUP (NAME) values ('RECEPCION');

insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Cazuela de Vacuno', 'Vacuno, cebolla, zanahoria, papa, zapallo, arroz', CURRENT_TIMESTAMP, 10900, 1, 'cazuela-de-vacuno.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Charquican con huevo', 'Receta tradicional de Charquicán con huevo frito encima', CURRENT_TIMESTAMP, 7900, 1, 'charquican-con-huevo.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Chunchules con papas cocidas', 'Chunchules de vacuno fritos acompañados de papas', CURRENT_TIMESTAMP, 9900, 1, 'chunchules-con-papas-cocidas.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Chupe de Guatitas', '', CURRENT_TIMESTAMP, 7900, 1, 'chupe-de-guatitas.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Consomé de Pollo', '', CURRENT_TIMESTAMP, 7900, 1, 'consome-de-pollo.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Costillar con papas fritas', '', CURRENT_TIMESTAMP, 11900, 1, 'costillar-con-papas-fritas.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Empanada de Queso', 'Empanada de queso frita acompañada de pebre', CURRENT_TIMESTAMP, 1900, 1, 'empanada-de-queso.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Paila Marina', '', CURRENT_TIMESTAMP, 11900, 1, 'paila-marina.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Pastel de Choclo', '', CURRENT_TIMESTAMP, 7900, 1, 'pastel-de-choclo.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Pernil al horno con papas rústicas', '', CURRENT_TIMESTAMP, 7900, 1, 'pernil-al-horno-con-papas-rusticas.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Pescado frito', 'Merluza frita', CURRENT_TIMESTAMP, 5900, 1, 'pescado-frito.jpg', 1);
insert into PRODUCTOS_RECETA (NOMBRE, DESCRIPCION, FECHA_INGRESO, PRECIO, ESTA_DISPONIBLE, IMAGEN, CATEGORIA_ID)
values ('Riñones con arroz', '', CURRENT_TIMESTAMP, 8900, 1, 'riñones-con-arroz.jpg', 1);


commit;
