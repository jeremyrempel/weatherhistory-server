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
  station_code CHAR(11) REFERENCES stations (station_code) NOT NULL,
  month INT NOT NULL,
  maxtemp DECIMAL NOT NULL,
  mintemp DECIMAL NOT NULL,
  precip DECIMAL NOT NULL
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO weather;