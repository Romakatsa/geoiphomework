DROP TABLE IF EXISTS ip_location_test CASCADE;
CREATE TABLE ip_location_test (
	ip_from bigint NOT NULL,
	ip_to bigint NOT NULL,
	country_code character(2) NOT NULL,
	country_name character varying(64) NOT NULL,
	region_name character varying(128) NOT NULL,
	city_name character varying(128) NOT NULL,
	latitude real NOT NULL,
	longitude real NOT NULL
);
ALTER TABLE ip_location_test DROP CONSTRAINT IF EXISTS ip_location_test_pkey CASCADE;
ALTER TABLE ip_location_test ADD CONSTRAINT ip_location_test_pkey PRIMARY KEY (ip_from, ip_to);
/*CREATE INDEX ip_location_gist_index ON ip_location_test USING GIST (int8range(ip_from,ip_to) range_ops);
*/