/* schemas definitions */
CREATE SCHEMA main;

/* main schema tables */
CREATE TABLE main.route (
  "id" BIGSERIAL PRIMARY KEY,
  "avg_passengers_per_day" INT
);

CREATE TABLE main.bus (
  "id" BIGSERIAL PRIMARY KEY,
  "route_id" BIGINT,
  "registration_number" VARCHAR(12) NOT NULL,
  "capacity" INT NOT NULL,
  "is_double_decker" BOOLEAN NOT NULL,
  CONSTRAINT bus_fk_route_id FOREIGN KEY (route_id) REFERENCES main.route(id) ON DELETE SET NULL
);

CREATE TABLE main.route_part (
  "id" BIGSERIAL PRIMARY KEY,
  "route_id" BIGINT NOT NULL,
  "position" INT NOT NULL,
  CONSTRAINT route_part_fk_route_id FOREIGN KEY (route_id) REFERENCES main.route(id) ON DELETE CASCADE
);

CREATE TABLE main.driver (
  "id" BIGSERIAL PRIMARY KEY,
  "route_part_id" BIGINT,
  "first_name" VARCHAR(20) NOT NULL,
  "last_name" VARCHAR(30) NOT NULL,
  "date_of_birth" DATE NOT NULL,
  "address" VARCHAR(100) NOT NULL,
  "driver_licence_number" VARCHAR(20),
  "phone_number" VARCHAR(20),
  CONSTRAINT driver_fk_route_part_id FOREIGN KEY (route_part_id) REFERENCES main.route_part(id) ON DELETE SET NULL
);

CREATE TABLE main.town (
  "id" BIGSERIAL PRIMARY KEY,
  "name" VARCHAR(20) NOT NULL,
  "country_code" VARCHAR(3) NOT NULL,
  "region" VARCHAR(30),
  "latitude" DOUBLE PRECISION NOT NULL,
  "longitude" DOUBLE PRECISION NOT NULL
);

CREATE TABLE main.garage (
  "id" BIGSERIAL PRIMARY KEY,
  "town_id" BIGINT NOT NULL,
  "name" VARCHAR(30) NOT NULL,
  "address" VARCHAR(100) NOT NULL,
  "capacity" INT NOT NULL,
  CONSTRAINT garage_fk_town_id FOREIGN KEY (town_id) REFERENCES main.town(id) ON DELETE CASCADE
);

CREATE TABLE main.transit (
  "id" BIGSERIAL PRIMARY KEY,
  "route_part_id" BIGINT NOT NULL,
  "from_town_id" BIGINT NOT NULL,
  "to_town_id" BIGINT NOT NULL,
  "position" INT NOT NULL,
  CONSTRAINT transit_fk_route_part_id FOREIGN KEY (route_part_id) REFERENCES main.route_part(id) ON DELETE CASCADE,
  CONSTRAINT transit_fk_from_town_id FOREIGN KEY (from_town_id) REFERENCES main.town(id) ON DELETE CASCADE,
  CONSTRAINT transit_fk_to_town_id FOREIGN KEY (to_town_id) REFERENCES main.town(id) ON DELETE CASCADE
);