package com.geoip;

import com.geoip.model.LocationIp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-fullDatabaseTest.properties")
public class LocationIpIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void importedCsvIntegrationTestCanonicalIp() {

        //first ip in a test csv
        String testCanonicalIp = "0.0.0.0";
        ResponseEntity<LocationIp> locationIpResponse = restTemplate.getForEntity("/geoip/" + testCanonicalIp, LocationIp.class);

        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(locationIpResponse.getBody().getCountryCode().equals("-"));
        assertThat(Long.compare(locationIpResponse.getBody().getDecimalIPv4(),0));

        // last ip in a test csv
        String lastCanonicalIp = "1.1.184.255";
        locationIpResponse = restTemplate.getForEntity("/geoip/" + lastCanonicalIp, LocationIp.class);

        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(locationIpResponse.getBody().getCountryCode().equals("TH"));
        assertThat(Long.compare(locationIpResponse.getBody().getDecimalIPv4(),16890111)).isEqualTo(0);

    }

    @Test
    public void importedCsvIntegrationTestDecimalIp() {

        //first ip in a test csv
        String decimalIp = "0";
        ResponseEntity<LocationIp> locationIpResponse = restTemplate.getForEntity("/geoip/" + decimalIp, LocationIp.class);

        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(locationIpResponse.getBody().getCountryCode().equals("-"));
        assertThat(locationIpResponse.getBody().getCanonicalIPv4().equals("0.0.0.0"));

        // last ip in a test csv
        String lastDecimalIp = "16890111";
        locationIpResponse = restTemplate.getForEntity("/geoip/" + lastDecimalIp, LocationIp.class);

        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(locationIpResponse.getBody().getCountryCode().equals("TH"));
        assertThat(locationIpResponse.getBody().getCanonicalIPv4().equals("1.1.184.255"));
    }

}
