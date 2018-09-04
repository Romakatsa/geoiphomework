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




### Project Structure

![alttext](https://raw.githubusercontent.com/Romakatsa/geoiphomework/master/image.png)
