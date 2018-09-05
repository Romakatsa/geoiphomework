package com.geoip;

import com.geoip.model.LocationIp;
import com.geoip.model.compositeId.LocationIpId;
import com.geoip.service.LocationIpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class LocationIpControllerTest {

    @MockBean
    private LocationIpService locationIpService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getRequestWithValidCanonicalIp() {

        String canonicalIp = "1.0.0.100";
        LocationIp testLoc = new LocationIp();
        LocationIpId testLocId = new LocationIpId();
        testLocId.setIpTo(0);
        testLocId.setIpFrom(16777471);
        testLoc.setId(testLocId);
        testLoc.setCountryCode("TS");

        given(locationIpService.findLocationByIp(canonicalIp))
                .willReturn(testLoc);

        // when
        ResponseEntity<LocationIp> locationIpResponse =
                restTemplate.getForEntity("/geoip/" + canonicalIp, LocationIp.class);

        // then
        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(locationIpResponse.getBody().getCountryCode().equals("TS"));
        assertThat(locationIpResponse.getBody().getCountryCode().equals("TS"));
    }

    @Test
    public void getRequestWithInvalidCanonicalIp() {

        String invalidCanonicalIp = "500.0.0.100";
        given(locationIpService.findLocationByIp(invalidCanonicalIp))
                .willThrow(new IllegalArgumentException());

        // when
        // error string is expected
        ResponseEntity<String> locationIpResponse =
                restTemplate.getForEntity("/geoip/" + invalidCanonicalIp, String.class);

        // then
        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getRequestWithValidDecimalIp() {

        String decimalIp = "16777316";
        LocationIp testLoc = new LocationIp();
        LocationIpId testLocId = new LocationIpId();
        testLocId.setIpTo(0);
        //testLocId.setIpFrom(16777471);
        testLoc.setId(testLocId);
        testLoc.setCountryCode("TS");

        given(locationIpService.findLocationByIp(decimalIp))
                .willReturn(testLoc);

        // when
        ResponseEntity<LocationIp> locationIpResponse =
                restTemplate.getForEntity("/geoip/" + decimalIp, LocationIp.class);

        // then
        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(locationIpResponse.getBody().getCountryCode().equals("TS"));
    }

    @Test
    public void getRequestWithInvalidDecimalIp() {

        String invalidDecimalIp = "500.0.0.100";
        given(locationIpService.findLocationByIp(invalidDecimalIp))
                .willThrow(new IllegalArgumentException());

        // when
        // error string is expected
        ResponseEntity<String> locationIpResponse =
                restTemplate.getForEntity("/geoip/" + invalidDecimalIp, String.class);

        // then
        assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
