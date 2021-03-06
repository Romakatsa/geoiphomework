DROP TABLE IF EXISTS ip_location CASCADE;
CREATE TABLE ip_location (
  ip_from      BIGINT                 NOT NULL,
  ip_to        BIGINT                 NOT NULL,
  country_code CHARACTER(2)           NOT NULL,
  country_name CHARACTER VARYING(64)  NOT NULL,
  region_name  CHARACTER VARYING(128) NOT NULL,
  city_name    CHARACTER VARYING(128) NOT NULL,
  latitude     REAL                   NOT NULL,
  longitude    REAL                   NOT NULL
);
ALTER TABLE ip_location
  DROP CONSTRAINT IF EXISTS ip_location_pkey CASCADE;
ALTER TABLE ip_location
  ADD CONSTRAINT ip_location_pkey PRIMARY KEY (ip_from, ip_to);
/*CREATE INDEX ip_location_gist_index ON ip_location_test USING GIST (int8range(ip_from,ip_to) range_ops);
*/