/* Set schemas to work with */
SET search_path TO main, public;

/* Routes */
INSERT INTO route(avg_passengers_per_day) VALUES ( 125 ); /* Shymkent - Almaty */
INSERT INTO route(avg_passengers_per_day) VALUES ( 271 ); /* Almaty - Karaganda - Nur-Sultan */
INSERT INTO route(avg_passengers_per_day) VALUES ( 189 ); /* Nur-Sultan - Shchuchinsk - Kokshetau - Kostanay */
INSERT INTO route(avg_passengers_per_day) VALUES ( 193 ); /* Kostanay - Chelyabinsk - Yekaterinburg */
INSERT INTO route(avg_passengers_per_day) VALUES ( 513 ); /* Nizhny Novgorod - Moscow - Yaroslavl */

/* Buses */
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (1, 'KZ726GK01', 65, false);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (2, 'KZ146GK01', 120, true);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (2, 'KZ067TY01', 50, true);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (3, 'KZ335AK10', 75, false);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (3, 'KZ560AK10', 75, false);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (4, 'KZ767LK10', 65, false);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (4, 'KZ112LK10', 75, false);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (5, 'P097VK77RUS', 100, true);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (5, 'P174VK77RUS', 100, true);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (5, 'P213VK77RUS', 100, true);
INSERT INTO bus(route_id, registration_number, capacity, is_double_decker) VALUES (5, 'C227BK77RUS', 75, false);

/* Routes_parts */
INSERT INTO route_part(route_id, position) VALUES (1, 1);
INSERT INTO route_part(route_id, position) VALUES (2, 1);
INSERT INTO route_part(route_id, position) VALUES (3, 1);
INSERT INTO route_part(route_id, position) VALUES (3, 2);
INSERT INTO route_part(route_id, position) VALUES (4, 1);
INSERT INTO route_part(route_id, position) VALUES (4, 2);
INSERT INTO route_part(route_id, position) VALUES (5, 1);
INSERT INTO route_part(route_id, position) VALUES (5, 2);

/* Drivers */
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
1, 'Arman', 'Aymagambetov', '1985-02-05', 'Almaty Kozybaeyva 183, apt. 25', '207541', '+77071263944'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
1, 'Daniyar', 'Isatayev', '1978-08-24', 'Shymkent Abaya 14, apt. 47', '189122', '+77059274315'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
2, 'Inokentiy', 'Vlasov', '1967-11-03', 'Karaganda Gogol 74, apt. 9', '174888', '+77059163315'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
2, 'Ahmet', 'Sarkenyan', '1991-02-17', 'Nur-Sultan Kabanbay batyr 123, apt. 167', '217903', '+77770538977'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
3, 'Ivan', 'Samoylov', '1993-10-21', 'Shchuchinsk Abaya 67', '219122', '+77781997888'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
4, 'Aleksandr', 'Spiridonov', '1984-06-06', 'Kokshetau Zheleznodorozhnay 12, apt. 2', '200133', '+77779998812'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
4, 'Rinat', 'Fakhrutdinov', '1993-11-01', 'Kostanay Kaiyrbekov 75, apt. 32', '219991', '+77077664545'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
5, 'Kim', 'Ivanov', '1979-02-22', 'Kostanay Shayahmetov 41', '205700', '+77057059899'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
6, 'Dmitriy', 'Letunov', '1987-09-15', 'Yekaterinburg Tolstoy 245, apt. 7', '200981', '+778913091324'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
7, 'Valeriy', 'Kolesnikov', '1979-08-30', 'Nizhny Novgorod Pushkin 7, apt. 79', '201112', '+7745120931'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
8, 'Omar', 'Fazylov', '1996-04-29', 'Moscow Naberezhniy 67, apt. 156', '202751', '+77791230165'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
8, 'Dmitriy', 'Haratyan', '1982-01-02', 'Moscow Kopeiynaya 97, apt. 11', '199712', '+77770341799'
);
INSERT INTO driver(route_part_id, first_name, last_name, date_of_birth, address,
                   driver_licence_number, phone_number) VALUES (
8, 'Igor', 'Semenov', '1990-09-01', 'Yaroslavl Lermontov 178', '206008', '+7792312364723'
);

/* Towns */
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Shymkent', 'KAZ', NULL, 42.296, 69.5999
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Almaty', 'KAZ', NULL, 43.2775, 76.895833
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Karaganda', 'KAZ', 'Karaganda Region', 49.802803, 73.087898
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Nur-Sultan', 'KAZ', NULL, 51.166667, 71.433333
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Shchuchinsk', 'KAZ', 'Akmola Region', 52.936385, 70.182669
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Kokshetau', 'KAZ', 'Akmola Region', 53.283333, 69.383333
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Kostanay', 'KAZ', 'Kostanay Region', 53.211871, 63.632564
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Chelyabinsk', 'RUS', 'Chelyabinsk Oblast', 55.154722, 61.375833
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Yekaterinburg', 'RUS', 'Sverdlovsk Oblast', 55.154722, 61.375833
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Nizhny Novgorod', 'RUS', 'Nizhny Novgorod Oblast', 56.326944, 44.0075
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Moscow', 'RUS', 'Central Federal District', 55.755833, 37.617222
);
INSERT INTO town(name, country_code, region, latitude, longitude) VALUES (
'Yaroslavl', 'RUS', 'Yaroslavl Oblast', 57.616667, 39.8506
);

/* Garages */
INSERT INTO garage(town_id, name, address, capacity) VALUES (2, 'GARAGE 55', 'Proletarskaya 207', 7);
INSERT INTO garage(town_id, name, address, capacity) VALUES (3, 'Angar K1', 'Promishlenaya 61', 15);
INSERT INTO garage(town_id, name, address, capacity) VALUES (7, 'Big Anchor storage', 'Doshanova 17', 10);
INSERT INTO garage(town_id, name, address, capacity) VALUES (12, 'Transit area', 'Voldaeva 81', 18);

/* Transits */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (1, 1, 2, 1); /* Shymkent - Almaty */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (2, 2, 3, 1); /* Almaty - Karaganda */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (2, 3, 4, 2); /* Karaganda - Nur-Sultan */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (3, 4, 5, 1); /* Nur-Sultan - Shchuchinsk */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (3, 5, 6, 2); /* Shchuchinsk - Kokshetau */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (4, 6, 7, 1); /* Kokshetau - Kostanay */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (5, 7, 8, 1); /* Kostanay - Chelyabinsk */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (6, 8, 9, 1); /* Chelyabinsk - Yekaterinburg */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (7, 10, 11, 1); /* Nizhny Novgorod - Moscow */
INSERT INTO transit(route_part_id, from_town_id, to_town_id, position) VALUES (8, 11, 12, 1); /* Moscow - Yaroslavl */