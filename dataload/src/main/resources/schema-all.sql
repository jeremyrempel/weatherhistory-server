DROP TABLE IF EXISTS countries;

CREATE TABLE countries (
  country_code CHAR(2) PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS stations;

CREATE TABLE stations (
    station_code CHAR(11) PRIMARY KEY,
    country_code CHAR(2) REFERENCES countries (country_code) NOT NULL,
    name VARCHAR(30) NOT NULL,
    location GEOGRAPHY(POINT) NOT NULL,
    elevation NUMERIC(6, 2),
    state CHAR(2)
);

DROP TABLE IF EXISTS cities;

CREATE TABLE cities (
  geonameid INT PRIMARY KEY,
  name VARCHAR (200),
  location GEOGRAPHY(POINT) NOT NULL,
  country_code CHAR(2) NOT NULL,
  station_code CHAR(11) REFERENCES stations (station_code) NOT NULL
);

DROP TABLE IF EXISTS monthlyavg;

CREATE TABLE monthlyavg (
  id SERIAL PRIMARY KEY,
  station_code CHAR(11) REFERENCES stations (station_code) NOT NULL,
  month INT NOT NULL,
  maxtemp DECIMAL NOT NULL,
  mintemp DECIMAL NOT NULL,
  precip DECIMAL NOT NULL
);

CREATE UNIQUE INDEX monthlyavg_idx1 ON monthlyavg (station_code, month);


CREATE OR REPLACE VIEW locations AS
SELECT
 c.name,
 c.station_code,
 c.country_code,
 s.state,
 c.location
FROM cities c
JOIN stations s ON c.station_code = s.station_code;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO weather;
GRANT ALL ON monthlyavg_id_seq TO weather;