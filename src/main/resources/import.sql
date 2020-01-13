/* Populate tabla clientes */
INSERT INTO regiones (id, nombre) values(1, "Sudamerica");
INSERT INTO regiones (id, nombre) values(2, "Centroamerica");
INSERT INTO regiones (id, nombre) values(3, "Norteamerica");
INSERT INTO regiones (id, nombre) values(4, "Europa");
INSERT INTO regiones (id, nombre) values(5, "Asia");
INSERT INTO regiones (id, nombre) values(6, "Africa");
INSERT INTO regiones (id, nombre) values(7, "Oceanía");
INSERT INTO regiones (id, nombre) values(8, "Antártida");

INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Juan", "Perez", "jaun@gmail.com", "2019-12-21", 1);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Pepe", "Soto", "pepe@gmail.com", "2019-12-21", 2);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Sergio", "Ramos", "sergio@gmail.com", "2019-12-21", 3);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Sandra", "Hernandez", "sandra@gmail.com", "2019-12-21", 4);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Luis", "Ochoa", "luis@gmail.com", "2019-12-21", 5);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Carmen", "Toro", "carmen@gmail.com", "2019-12-21", 6);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("John", "Ramirez", "john@gmail.com", "2019-12-21", 7);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Federico", "Aguirre", "federico@gmail.com", "2019-12-21", 8);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Silvia", "Torso", "silvia@gmail.com", "2019-12-21", 1);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Ernesto", "Cuervo", "ernesto@gmail.com", "2019-12-21", 2);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Jose", "Piedrahita", "jose@gmail.com", "2019-12-21", 3);
INSERT INTO clientes (nombre, apellido, email, created_at, region_id) values("Alcides", "Cossio", "alcides@gmail.com", "2019-12-21", 4);

INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES('john', '$2a$10$AqfIX3Cr4hxWuDSfbqRG.uY.FTuH4mp93TNhCMJWhVLP.xX65XWI.', 1, "John", "Piedrahita", "user@admin.com");
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES('admin', '$2a$10$lNUCR25dsX7g.VMTOO.rdeGnt4szzrznHwcrWqqwD0INXpywmCSU2', 1, "Admin", "Admin", "admin@admin.com");

INSERT INTO roles (nombre) VALUES ("ROLE_USER");
INSERT INTO roles (nombre) VALUES ("ROLE_ADMIN");

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES(2, 1);

INSERT INTO productos (nombre, precio, created_at) VALUES("Panasonic Pantalla LCD", 2559900, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES("Sony Camara digital DSC-W320B", 123490, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES("Apple iPod shuffle", 1499990, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES("Sony Notebook Z110", 37990, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES("Hewlett Packard Multifuncional F2280", 69990, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES("Bianchi Bicicleta Arp 26", 69900, NOW());
INSERT INTO productos (nombre, precio, created_at) VALUES("Mica Comoda 5 Cajones", 299990, NOW());

INSERT INTO facturas (descripcion, observacion, cliente_id, created_at) VALUES("Factura equipos de oficina", null, 1, NOW());

INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, created_at) VALUES("Factura bicicleta", "Alguna nota importante!", 2, NOW());

INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

