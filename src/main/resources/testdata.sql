TRUNCATE TABLE ip_location;
INSERT INTO ip_location (ip_from, ip_to, country_code, country_name, city_name, region_name, latitude, longitude)
VALUES
  (16777216, 16777471, 'AU', 'Australia', 'Queensland', 'Brisbane', -27.467940, 153.028090),
  (16777472, 16778239, 'CN', 'China', 'Fujian', 'Fuzhou', 26.061390, 119.306110),
  (16778240, 16779263, 'AU', 'Australia', 'Victoria', 'Melbourne', -37.814000, 144.963320),
  (16889856, 16890111, 'TH', 'Thailand', 'Nong Bua Lam Phu', 'Na Klang', 17.307200, 102.188860);