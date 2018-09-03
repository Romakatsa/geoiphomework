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

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Roma on 03.09.2018.
 */
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
        public void getRequestWithValidIp() {

            String testCanonicalIp = "1.0.0.100";
            LocationIp testLoc = new LocationIp();
            LocationIpId testLocId = new LocationIpId();
            testLocId.setIpTo(0);
            testLocId.setIpFrom(16777471);
            testLoc.setId(testLocId);
            testLoc.setCountryCode("TS");

            given(locationIpService.findLocationByCanonicalIP(testCanonicalIp))
                    .willReturn(testLoc);

            // when
            ResponseEntity<LocationIp> locationIpResponse = restTemplate.getForEntity("/geoip/"+testCanonicalIp, LocationIp.class);

            // then
            assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(locationIpResponse.getBody().getCountryCode().equals("TS"));
        }

        @Test
        public void getRequestWithInvalidIp() {

            String testInvalidCanonicalIp = "500.0.0.100";
            given(locationIpService.findLocationByCanonicalIP(testInvalidCanonicalIp))
                    .willThrow(new IllegalArgumentException());

            // when
            // error string is expected
            ResponseEntity<String> locationIpResponse = restTemplate.getForEntity("/geoip/"+testInvalidCanonicalIp, String.class);

            // then
            assertThat(locationIpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

}
