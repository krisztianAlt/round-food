INSERT INTO customer VALUES (1, 'berci@freemail.hu', 'Bertalan', 'Farkas', 'USER', '$2a$10$8Kre7KpW8MXDqWJ3uh/a.eNiQAoeT0ONaZtg38UDm.Lz4U7o.LZSm', '30-1234567');
INSERT INTO customer VALUES (2, 'atmos@freemail.hu', 'Jimmy', 'Hendrix', 'USER', '$2a$10$8Kre7KpW8MXDqWJ3uh/a.eNiQAoeT0ONaZtg38UDm.Lz4U7o.LZSm', '20-7654321');

SELECT pg_catalog.setval('customer_id_seq', 2, true);


INSERT INTO shippingaddress VALUES (1, 'Hold utca', 'Budapest', '1033', 1);
INSERT INTO shippingaddress VALUES (2, 'Meteor köz', 'Budapest', '1134', 1);

SELECT pg_catalog.setval('shippingaddress_id_seq', 2, true);