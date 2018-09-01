package com.geoip;

import com.geoip.model.LocationIp;
import com.geoip.model.compositeId.LocationIpId;
import com.geoip.repository.LocationIpRepository;
import com.geoip.service.LocationIpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Roma on 01.09.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class LocationIpRepositoryTest {


    @Autowired
    private LocationIpService service;

    @Autowired
    private LocationIpRepository repository;

    @Test
    public void testIpLocation() throws Exception {

        long ipFrom = 123456789;  // = 7.91.205.21
        long ipTo = 223456789; //10^8 addresses range = 13.81.174.21

        LocationIp locIp = new LocationIp();
        LocationIpId locIpId = new LocationIpId();
        locIpId.setIpFrom(ipFrom);
        locIpId.setIpTo(ipTo);
        locIp.setId(locIpId);
        locIp.setCityName("Rovaniemi");
        locIp.setRegionName("Korvatunturi");
        locIp.setCountryName("Lappland");
        locIp.setCountryCode("LP");
        locIp.setLatitude(66.497022d);
        locIp.setLongitude(25.724999d);

        String testCanonicalIp = decimalToCanonicalIp(ipFrom + (long) Math.random()*(ipTo - ipFrom));

        repository.saveAndFlush(locIp);
        LocationIp repIpLoc = service.findLocationByCanonicalIP(testCanonicalIp);
        assertThat(repIpLoc.getCountryName()).isEqualTo("Lappland");
        String outOfRangeCanonicalIp = decimalToCanonicalIp(ipTo+1);
        repIpLoc = service.findLocationByCanonicalIP(outOfRangeCanonicalIp);
        assertThat(repIpLoc).isNull();
    }

    private String decimalToCanonicalIp(long ip) {
            return ((ip >> 24) & 0xFF) + "."
                    + ((ip >> 16) & 0xFF) + "."
                    + ((ip >> 8) & 0xFF) + "."
                    + (ip & 0xFF);

    }



}
