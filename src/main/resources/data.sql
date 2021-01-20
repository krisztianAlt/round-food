INSERT INTO customer VALUES (1, 'berci@freemail.hu', 'Bertalan', 'Farkas', 'USER', '$2a$10$8Kre7KpW8MXDqWJ3uh/a.eNiQAoeT0ONaZtg38UDm.Lz4U7o.LZSm', '12324');
INSERT INTO customer VALUES (2, 'atmos@freemail.hu', 'Jimmy', 'Hendrix', 'USER', '$2a$10$8Kre7KpW8MXDqWJ3uh/a.eNiQAoeT0ONaZtg38UDm.Lz4U7o.LZSm', '56789');

SELECT pg_catalog.setval('customer_id_seq', 2, true);
