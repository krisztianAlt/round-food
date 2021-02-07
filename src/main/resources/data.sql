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
INSERT INTO extratopping VALUES (9, 'Cheese', 100);
INSERT INTO extratopping VALUES (10, 'Feta', 150);
INSERT INTO extratopping VALUES (11, 'Olive', 100);
INSERT INTO extratopping VALUES (12, 'Onion', 100);
INSERT INTO extratopping VALUES (13, 'Pepperoni', 140);
INSERT INTO extratopping VALUES (14, 'Pineapple', 200);
INSERT INTO extratopping VALUES (15, 'Roquefort', 150);
INSERT INTO extratopping VALUES (16, 'Sun dried tomatoes', 200);
INSERT INTO extratopping VALUES (17, 'Marmalade', 400);
INSERT INTO extratopping VALUES (18, 'Peanut butter', 300);
INSERT INTO extratopping VALUES (19, 'Rice syrup (instead of sugar)', 600);
INSERT INTO extratopping VALUES (20, 'Szatmári plum jam', 300);
INSERT INTO extratopping VALUES (21, 'Vanilla custard', 300);

SELECT pg_catalog.setval('extratopping_id_seq', 21, true);


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
INSERT INTO food VALUES (5, '20 pieces. For music fans.', 'CAKE', 'Music Tower', '11300');
INSERT INTO food VALUES (6, '32 cm. For vegetarians.', 'PIZZA', 'Italian Waves', '3000');
INSERT INTO food VALUES (7, '32 cm. A lot of ham underneath the arugula. With tomato sauce.', 'PIZZA', 'The Hungry Longshoreman', '2900');
INSERT INTO food VALUES (8, '32 cm. Salami slices, cheese scraps, tomato sauce.', 'PIZZA', 'Salami pizza', '2800');
INSERT INTO food VALUES (9, '32 cm. Egg, sausage, cheese, onion.', 'PIZZA', 'The Calorie Refiller', '2800');
INSERT INTO food VALUES (10, '25 cm. Slim pie, thick stuffing. Grinded nuts in the cream.', 'PIE', 'Nutcracker', '2000');
INSERT INTO food VALUES (11, '30 cm. Orange and orange and orange...', 'PIE', 'The Sun', '2300');

SELECT pg_catalog.setval('food_id_seq', 11, true);


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
INSERT INTO foodpicture VALUES (15, 'fodros1.jpg', 'Tomato cubes lives on the edge', 6);
INSERT INTO foodpicture VALUES (16, 'fodros2.jpg', 'Dried tomato on green bed', 6);
INSERT INTO foodpicture VALUES (17, 'rukkola1.jpg', 'Green blanket', 7);
INSERT INTO foodpicture VALUES (18, 'rukkola2.jpg', 'Ham and arugula', 7);
INSERT INTO foodpicture VALUES (19, 'rukkola3.jpg', 'Arugula and ham', 7);
INSERT INTO foodpicture VALUES (20, 'sausages1.jpg', 'In friendly hands', 8);
INSERT INTO foodpicture VALUES (21, 'sausages2.jpg', 'Well-baked margins', 8);
INSERT INTO foodpicture VALUES (22, 'sausages3.jpg', 'Eleven', 8);
INSERT INTO foodpicture VALUES (23, 'tojasos1.jpg', 'Rich offer', 9);
INSERT INTO foodpicture VALUES (24, 'tojasos2.jpg', 'Multicolor meal', 9);
INSERT INTO foodpicture VALUES (25, 'bakedpie2.jpg', 'Sweet pac-man', 10);
INSERT INTO foodpicture VALUES (26, 'bakedpie1.jpg', 'A slice of pie', 10);
INSERT INTO foodpicture VALUES (27, 'narancspite1.jpg', 'At the center of the solar system', 11);
INSERT INTO foodpicture VALUES (28, 'narancspite2.jpg', 'Still-life', 11);
INSERT INTO foodpicture VALUES (29, 'narancspite3.jpg', 'The big and the small', 11);
INSERT INTO foodpicture VALUES (30, 'narancspite4.jpg', 'In and out', 11);

SELECT pg_catalog.setval('foodpicture_id_seq', 30, true);


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
INSERT INTO food_extra_toppings VALUES (6, 9);
INSERT INTO food_extra_toppings VALUES (6, 11);
INSERT INTO food_extra_toppings VALUES (6, 12);
INSERT INTO food_extra_toppings VALUES (6, 14);
INSERT INTO food_extra_toppings VALUES (6, 16);
INSERT INTO food_extra_toppings VALUES (7, 9);
INSERT INTO food_extra_toppings VALUES (7, 10);
INSERT INTO food_extra_toppings VALUES (7, 13);
INSERT INTO food_extra_toppings VALUES (7, 15);
INSERT INTO food_extra_toppings VALUES (7, 16);
INSERT INTO food_extra_toppings VALUES (8, 9);
INSERT INTO food_extra_toppings VALUES (8, 10);
INSERT INTO food_extra_toppings VALUES (8, 11);
INSERT INTO food_extra_toppings VALUES (8, 12);
INSERT INTO food_extra_toppings VALUES (8, 13);
INSERT INTO food_extra_toppings VALUES (8, 15);
INSERT INTO food_extra_toppings VALUES (8, 16);
INSERT INTO food_extra_toppings VALUES (9, 9);
INSERT INTO food_extra_toppings VALUES (9, 11);
INSERT INTO food_extra_toppings VALUES (9, 12);
INSERT INTO food_extra_toppings VALUES (9, 15);
INSERT INTO food_extra_toppings VALUES (10, 18);
INSERT INTO food_extra_toppings VALUES (10, 19);
INSERT INTO food_extra_toppings VALUES (10, 21);
INSERT INTO food_extra_toppings VALUES (10, 8);
INSERT INTO food_extra_toppings VALUES (11, 17);
INSERT INTO food_extra_toppings VALUES (11, 19);
INSERT INTO food_extra_toppings VALUES (11, 20);
