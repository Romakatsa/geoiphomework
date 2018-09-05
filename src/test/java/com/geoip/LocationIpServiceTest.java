package com.geoip;

import com.geoip.model.LocationIp;
import com.geoip.service.LocationIpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class LocationIpServiceTest {


    @Autowired
    private LocationIpService service;

    @Test
    public void testValidCanonicalIpLocation() throws Exception {

        String testCanonicalIp = "1.0.0.100";  //from testdata.sql
        LocationIp repIpLoc = service.findLocationByIp(testCanonicalIp);
        assertThat(repIpLoc.getCountryName()).isEqualTo("Australia");
    }

    @Test(expected = NoSuchElementException.class)
    public void testAbsentCanonicalIpLocation() throws Exception {

        String testCanonicalIp = "100.100.100.100"; //no ip in testdata.sql
        LocationIp repIpLoc = service.findLocationByIp(testCanonicalIp);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalCanonicalIpLocation() throws Exception {

        String testCanonicalIp = "500.100.100.100"; //invalid ip
        LocationIp repIpLoc = service.findLocationByIp(testCanonicalIp);
    }

    @Test
    public void testValidDecimalIpLocation() throws Exception {

        String decimalIp = "16777316";  //from testdata.sql
        LocationIp repIpLoc = service.findLocationByIp(decimalIp);
        assertThat(repIpLoc.getCountryName()).isEqualTo("Australia");
    }

    @Test(expected = NoSuchElementException.class)
    public void testAbsentDecimalIpLocation() throws Exception {

        String decimalIp = "1684300900"; //no ip in testdata.sql
        LocationIp repIpLoc = service.findLocationByIp(decimalIp);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalDecimalIpLocation() throws Exception {

        String decimalIp = "9684300900"; //invalid ip > 4294967295L;
        LocationIp repIpLoc = service.findLocationByIp(decimalIp);
    }

    @Test
    public void testValidHexOctDecIpLocation1() throws Exception {
        //from testdata.sql 1.1.0xB8.0377 = 1.1.184.255
        String hexOctDecIp = "1.1.0xB8.0377";

        LocationIp repIpLoc = service.findLocationByIp(hexOctDecIp);
        assertThat(repIpLoc.getCountryName()).isEqualTo("Thailand");
    }

    @Test
    public void testValidHexOctDecIpLocation2() throws Exception {
        //from testdata.sql
        String hexOctDecIp = "1.1.0x000B8.0000377";  //zero-padded oct or hex parts

        LocationIp repIpLoc = service.findLocationByIp(hexOctDecIp);
        assertThat(repIpLoc.getCountryName()).isEqualTo("Thailand");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalHecOctDecIpLocation1() throws Exception {

        String testCanonicalIp = "1.1.0xB8.03797"; //invalid octal part.
        LocationIp repIpLoc = service.findLocationByIp(testCanonicalIp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalHecOctDecIpLocation2() throws Exception {

        String testCanonicalIp = "1.1.0xB9F.0377"; //too big hex part.
        LocationIp repIpLoc = service.findLocationByIp(testCanonicalIp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalHecOctDecIpLocation3() throws Exception {

        String testCanonicalIp = "1.1..0XFF"; //missing part
        LocationIp repIpLoc = service.findLocationByIp(testCanonicalIp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalHecOctDecIpLocation4() throws Exception {

        String testCanonicalIp = "1.1.0x.0XFF"; //missing numbers in hex part
        LocationIp repIpLoc = service.findLocationByIp(testCanonicalIp);
    }




}
