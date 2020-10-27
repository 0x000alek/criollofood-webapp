insert into GRUPOS (ID, NAME) values (1, 'ADMINISTRADOR');
insert into GRUPOS (ID, NAME) values (2, 'CLIENTE');
insert into GRUPOS (ID, NAME) values (3, 'BODEGA');
insert into GRUPOS (ID, NAME) values (4, 'FINANZAS');
insert into GRUPOS (ID, NAME) values (5, 'COCINA');

insert into PERMISOS (ID, NAME) values (1, 'permiso 1');
insert into PERMISOS (ID, NAME) values (2, 'permiso 2');
insert into PERMISOS (ID, NAME) values (3, 'permiso 3');
insert into PERMISOS (ID, NAME) values (4, 'permiso 4');
insert into PERMISOS (ID, NAME) values (5, 'permiso 5');

insert into GRUPO_PERMISOS (GRUPO_ID, PERMISO_ID) values (2, 1);
insert into GRUPO_PERMISOS (GRUPO_ID, PERMISO_ID) values (2, 2);
insert into GRUPO_PERMISOS (GRUPO_ID, PERMISO_ID) values (2, 3);
insert into GRUPO_PERMISOS (GRUPO_ID, PERMISO_ID) values (2, 4);
insert into GRUPO_PERMISOS (GRUPO_ID, PERMISO_ID) values (2, 5);

commit;
