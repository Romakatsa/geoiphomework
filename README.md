## geoiphomework

This is my homework project - spring boot rest web service with only one get endpoint host/geoip/{ipv4} (ex. localhost:8080/geoip/8.8.8.8}, 
returning JSON that contains following information about passed ipv4 address:
<pre>
{
  "canonicalIPv4Representation": "8.8.8.8",
  "cityName": "Mountain View",
  "countryCode": "US",
  "countryName": "United States",
  "IPv4": "134744072",               #Decimal IPv4 representation
  "latitude": "37.405992",
  "longitude": "-122.078515",
  "regionName": "California"
}
</pre>

data is used from https://lite.ip2location.com/database/ip-country-region-city-latitude-longitude

### Technologies used

  - Spring Boot 2.0.1
  - Spring Data 2.1.0 ( Hibernate 5.3.5 )
  - PostgreSQL 10
  - JUnit 4
  - Tomcat 9
  - Maven
  

### Run

#### Configure database manually
Execute following sql scripts in postgreSql:
<pre>
  CREATE DATABASE ip2location WITH ENCODING 'UTF8';
  \c ip2location
  CREATE TABLE ip2location_db5(
	  ip_from bigint NOT NULL,
	  ip_to bigint NOT NULL,
	  country_code character(2) NOT NULL,
	  country_name character varying(64) NOT NULL,
	  region_name character varying(128) NOT NULL,
	  city_name character varying(128) NOT NULL,
	  latitude real NOT NULL,
	  longitude real NOT NULL,
	  CONSTRAINT ip2location_db5_pkey PRIMARY KEY (ip_from, ip_to)
  );
</pre>
Populate from .csv file. Csv file must be located in postgres server data directory.
<pre>COPY ip2location_db5 FROM 'IP2LOCATION-LITE-DB5.CSV' WITH CSV QUOTE AS '"';</pre>
#### OR
#### Configure application properties (see project structure - resources) and build project

### Run Application
#### Maven
    <pre>mvn spring-boot:run</pre>
#### Deploy WAR package 
Copy geoiphomework.war archive to tomcat webApps folder and run server
#### Run JAR
execute <pre>java -Xms512m -geoiphomework.jar</pre> from command line
### Project Structure

![alttext](https://raw.githubusercontent.com/Romakatsa/geoiphomework/master/image.png)

#### PostgresCsvLoader
PostgresCsvLoader - is a class implementing ApplicationRunner interface used run method to populate database from csv file using postgres CopyManager performing 'Copy tblName From csvFile...'

#### resources:
application.properties - main app properties. Some lines are: 
<pre>
#schema for database creation
  spring.datasource.schema = classpath:/schema-main.sql
#run PostgresCsvLoader to populate database from csv
  customProps.populateDBFromCSV = true
#relative to resources folder. Csv file to populate from.
  customProps.pathToCsv = /ip2location_test.csv
</pre>

application-test.properties - properties for LocationIpControllerTest and LocationIpServiseTest tests. Defines
<pre>...
spring.datasource.data = classpath:/testdata.sql
...</pre>
sql script for populating test database with some entries

application-fullDatabaseTest.properties - properties for LocationIpIntegrationTest class. Defines
<pre>...
#schema for test database creation
  spring.datasource.schema = classpath:/schema-test.sql
#run PostgresCsvLoader to populate database from .csv
  customProps.populateDBFromCSV = false
#relative to resources folder. Csv file to populate test database from
  customProps.pathToCsv = /ip2location_test.csv
...</pre>

