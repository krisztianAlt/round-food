INSERT INTO customer VALUES (1, 'Hold utca', 'Budapest', 'berci@freemail.hu', 'Bertalan', 'Farkas', 'USER', '$2a$10$8Kre7KpW8MXDqWJ3uh/a.eNiQAoeT0ONaZtg38UDm.Lz4U7o.LZSm', '30-1234567', '1033');
INSERT INTO customer VALUES (2, 'Meteor köz', 'Budapest', 'atmos@freemail.hu', 'Jimmy', 'Hendrix', 'USER', '$2a$10$8Kre7KpW8MXDqWJ3uh/a.eNiQAoeT0ONaZtg38UDm.Lz4U7o.LZSm', '20-7654321', '1134');

SELECT pg_catalog.setval('customer_id_seq', 2, true);


INSERT INTO extratopping VALUES (1, 'Birthday candle pack', 1000);
INSERT INTO extratopping VALUES (2, 'Coconut flakes', 300);
INSERT INTO extratopping VALUES (3, 'Marzipan bear', 500);
INSERT INTO extratopping VALUES (4, 'Marzipan Boba Fett', 1000);
INSERT INTO extratopping VALUES (5, 'Marzipan Mozart', 1000);
INSERT INTO extratopping VALUES (6, 'Marzipan Wagner', 1000);
INSERT INTO extratopping VALUES (7, 'Marzipan orchestra', 22000);
INSERT INTO extratopping VALUES (8, 'Whipped cream', 300);

SELECT pg_catalog.setval('extratopping_id_seq', 8, true);


INSERT INTO paymentoption VALUES (1, 'Credit card');
INSERT INTO paymentoption VALUES (2, 'OTP SZÉP card');
INSERT INTO paymentoption VALUES (3, 'K&H SZÉP card');
INSERT INTO paymentoption VALUES (4, 'MKB SZÉP card');
INSERT INTO paymentoption VALUES (5, 'Cash to food courier');

SELECT pg_catalog.setval('paymentoption_id_seq', 5, true);


INSERT INTO paymentoptionpicture VALUES (1, 'amex.jpg', 'American Express', 1);
INSERT INTO paymentoptionpicture VALUES (2, 'mastercard.jpg', 'Mastercard', 1);
INSERT INTO paymentoptionpicture VALUES (3, 'visa.jpg', 'Visa', 1);
INSERT INTO paymentoptionpicture VALUES (4, 'otp.jpg', 'OTP SZÉP card', 2);
INSERT INTO paymentoptionpicture VALUES (5, 'kh.jpg', 'K&H SZÉP card', 3);
INSERT INTO paymentoptionpicture VALUES (6, 'mkb.jpg', 'MKB SZÉP card', 4);
INSERT INTO paymentoptionpicture VALUES (7, 'coins-solid.svg', 'Cash', 5);

SELECT pg_catalog.setval('paymentoptionpicture_id_seq', 5, true);


INSERT INTO food VALUES (1, '8 pieces from 4 cake, with strawberry, chocolate, orange and raspberry cream. Cookies at the top.', 'CAKE', 'The Colourful', '6000');
INSERT INTO food VALUES (2, '12 pieces chocolate cake.', 'CAKE', 'Chocolate cake', '7500');
INSERT INTO food VALUES (3, '8 pieces. Fruits at the top.', 'CAKE', 'Curd cheesecake', '5500');
INSERT INTO food VALUES (4, '8 pieces. Simple is better.', 'CAKE', 'Millstone', '3000');
INSERT INTO food VALUES (5, '20 pieces. For music fans.', 'CAKE', 'Music tower', '11300');

SELECT pg_catalog.setval('food_id_seq', 5, true);


INSERT INTO foodpicture VALUES (1, 'colorcake1.jpg', 'Colourful cake', 1);
INSERT INTO foodpicture VALUES (2, 'colorcake3.jpg', 'Mixed joy', 1);
INSERT INTO foodpicture VALUES (3, 'colorcake4.jpg', 'Strawberry part', 1);
INSERT INTO foodpicture VALUES (4, 'csoki2.jpg', 'Chocolate cake', 2);
INSERT INTO foodpicture VALUES (5, 'csoki1.jpg', 'Brown rose', 2);
INSERT INTO foodpicture VALUES (6, 'epres4.jpg', 'Curd cheesecake', 3);
INSERT INTO foodpicture VALUES (7, 'epres3.jpg', 'Light mousse', 3);
INSERT INTO foodpicture VALUES (8, 'epres1.jpg', 'The light side of the force', 3);
INSERT INTO foodpicture VALUES (9, 'natural1.jpg', 'Raw cake', 4);
INSERT INTO foodpicture VALUES (10, 'natural2.jpg', 'Delicious millstone', 4);
INSERT INTO foodpicture VALUES (11, 'natural3.jpg', 'Pac-Man', 4);
INSERT INTO foodpicture VALUES (12, 'natural4.jpg', 'Without frills', 4);
INSERT INTO foodpicture VALUES (13, 'zenetorta.jpg', 'Hommage to musical notes', 5);
INSERT INTO foodpicture VALUES (14, 'zenetorta3.jpg', 'White roses', 5);

SELECT pg_catalog.setval('foodpicture_id_seq', 14, true);


INSERT INTO food_extra_toppings VALUES (1, 1);
INSERT INTO food_extra_toppings VALUES (1, 2);
INSERT INTO food_extra_toppings VALUES (1, 3);
INSERT INTO food_extra_toppings VALUES (1, 4);
INSERT INTO food_extra_toppings VALUES (1, 8);
INSERT INTO food_extra_toppings VALUES (2, 1);
INSERT INTO food_extra_toppings VALUES (2, 3);
INSERT INTO food_extra_toppings VALUES (2, 8);
INSERT INTO food_extra_toppings VALUES (3, 1);
INSERT INTO food_extra_toppings VALUES (3, 2);
INSERT INTO food_extra_toppings VALUES (3, 8);
INSERT INTO food_extra_toppings VALUES (4, 1);
INSERT INTO food_extra_toppings VALUES (4, 2);
INSERT INTO food_extra_toppings VALUES (4, 3);
INSERT INTO food_extra_toppings VALUES (4, 4);
INSERT INTO food_extra_toppings VALUES (4, 8);
INSERT INTO food_extra_toppings VALUES (5, 1);
INSERT INTO food_extra_toppings VALUES (5, 2);
INSERT INTO food_extra_toppings VALUES (5, 5);
INSERT INTO food_extra_toppings VALUES (5, 6);
INSERT INTO food_extra_toppings VALUES (5, 7);
INSERT INTO food_extra_toppings VALUES (5, 8);