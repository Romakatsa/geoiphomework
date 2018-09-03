package com.geoip;

import com.geoip.model.LocationIp;
import com.geoip.model.compositeId.LocationIpId;
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
import static org.mockito.BDDMockito.given;

/**
 * Created by Roma on 03.09.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-fullDatabaseTest.properties")
public class LocationIpIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void importedCsvDatabaseIntegrationTest() {

        //first ip in a test csv
        String testCanonicalIp = "0.0.0.0";
        ResponseEntity<LocationIp> locationIpResponse = restTemplate.getForEntity("/geoip/" + testCanonicalIp, LocationIp.class);

        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(locationIpResponse.getBody().getCountryCode().equals("-"));

        // last ip in a test csv
        String lastCanonicalIp = "1.1.184.255";
        locationIpResponse = restTemplate.getForEntity("/geoip/" + lastCanonicalIp, LocationIp.class);

        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(locationIpResponse.getBody().getCountryCode().equals("TH"));

    }
}
