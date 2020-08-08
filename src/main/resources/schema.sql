/* schemas definitions */
CREATE SCHEMA main;

/* main schema tables */
CREATE TABLE main.route (
  "id" BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  "avg_passengers_per_day" INT
);

CREATE TABLE main.bus (
  "id" BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  "route_id" BIGINT NOT NULL,
  "registration_number" VARCHAR(12) NOT NULL,
  "capacity" INT NOT NULL,
  "is_double_decker" BOOLEAN NOT NULL,
  CONSTRAINT fk_route_id FOREIGN KEY (route_id) REFERENCES main.route(id)
);

CREATE TABLE main.route_part (
  "id" BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  "route_id" BIGINT NOT NULL,
  "position" INT NOT NULL,
  CONSTRAINT fk_route_id FOREIGN KEY (route_id) REFERENCES main.route(id)
);

CREATE TABLE main.driver (
  "id" BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  "route_part_id" BIGINT,
  "first_name" VARCHAR(20) NOT NULL,
  "last_name" VARCHAR(30) NOT NULL,
  "date_of_birth" DATE NOT NULL,
  "address" VARCHAR(100) NOT NULL,
  "driver_licence_number" VARCHAR(20),
  "phone_number" VARCHAR(20),
  CONSTRAINT fk_route_part_id FOREIGN KEY (route_part_id) REFERENCES main.route_part(id)
);

CREATE TABLE main.town (
  "id" BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  "name" VARCHAR(20) NOT NULL,
  "country_code" VARCHAR(3) NOT NULL,
  "region" VARCHAR(30),
  "latitude" DOUBLE PRECISION NOT NULL,
  "longitude" DOUBLE PRECISION NOT NULL
);

CREATE TABLE main.garage (
  "id" BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  "town_id" BIGINT NOT NULL,
  "name" VARCHAR(20) NOT NULL,
  "address" VARCHAR(100) NOT NULL,
  "capacity" INT NOT NULL,
  CONSTRAINT fk_town_id FOREIGN KEY (town_id) REFERENCES main.town(id)
);

CREATE TABLE main.transit (
  "id" BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  "route_part_id" BIGINT NOT NULL,
  "from_town_id" BIGINT NOT NULL,
  "to_town_id" BIGINT NOT NULL,
  "position" INT NOT NULL,
  CONSTRAINT fk_route_part_id FOREIGN KEY (route_part_id) REFERENCES main.route_part(id),
  CONSTRAINT fk_from_town_id FOREIGN KEY (from_town_id) REFERENCES main.town(id),
  CONSTRAINT fk_to_town_id FOREIGN KEY (to_town_id) REFERENCES main.town(id)
);