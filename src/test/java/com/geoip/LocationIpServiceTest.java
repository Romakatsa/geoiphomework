package com.geoip;

import com.geoip.model.LocationIp;
import com.geoip.model.compositeId.LocationIpId;
import com.geoip.repository.LocationIpRepository;
import com.geoip.service.LocationIpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Roma on 01.09.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class LocationIpServiceTest {


    @Autowired
    private LocationIpService service;

    @Test
    public void testValidIpLocation() throws Exception {

        String testCanonicalIp = "1.0.0.100";  //from testdata.sql
        LocationIp repIpLoc = service.findLocationByCanonicalIP(testCanonicalIp);
        assertThat(repIpLoc.getCountryName()).isEqualTo("Australia");
    }

    @Test(expected = NoSuchElementException.class)
    public void testAbsentIpLocation() throws Exception {

        String testCanonicalIp = "100.100.100.100"; //no ip in testdata.sql
        LocationIp repIpLoc = service.findLocationByCanonicalIP(testCanonicalIp);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalIpLocation() throws Exception {

        String testCanonicalIp = "500.100.100.100"; //invalid ip
        LocationIp repIpLoc = service.findLocationByCanonicalIP(testCanonicalIp);
    }


}
