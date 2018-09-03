CREATE TABLE IF NOT EXISTS ip_location(
	ip_from bigint NOT NULL,
	ip_to bigint NOT NULL,
	country_code character(2) NOT NULL,
	country_name character varying(64) NOT NULL,
	region_name character varying(128) NOT NULL,
	city_name character varying(128) NOT NULL,
	latitude real NOT NULL,
	longitude real NOT NULL,
	CONSTRAINT ip_location_pkey PRIMARY KEY (ip_from, ip_to)
);

CREATE INDEX IF NOT EXISTS gist_range_index ON ip_location USING GIST (int8range(ip_from-1,ip_to+1) range_ops);
