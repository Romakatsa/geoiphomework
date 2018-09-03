package com.geoip.service;

import com.geoip.PostgresCsvLoader;
import com.geoip.model.LocationIp;
import com.geoip.repository.LocationIpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roma on 01.09.2018.
 */
@Service
public class LocationIpService {

    @Autowired
    private LocationIpRepository locationIpRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostgresCsvLoader.class);


    public LocationIp findLocationByCanonicalIP(String canonicalIp) {
        Long stTimeInSec = Instant.now().toEpochMilli();
        Pattern p = Pattern.compile(LocationIp.ipRegEx);
        Matcher m = p.matcher(canonicalIp);
        if (!m.matches()) throw new IllegalArgumentException("Argument ip address does not matches ip regex");

        long decimalIp = canonicalToDecimalIp(canonicalIp);
        LocationIp locationIp = locationIpRepository.findByIdIpFromLessThanEqualAndIdIpToGreaterThanEqual(decimalIp,decimalIp);
        if (locationIp != null){
            locationIp.setIp(canonicalIp);
        }
        Long endTimeInSec = Instant.now().toEpochMilli();
        logger.info("time fetching element from DB is {} ms", endTimeInSec - stTimeInSec);
        return locationIp;
    }

    public long canonicalToDecimalIp(String canonicalIp) {
        //Use following conversion logic:
        //192.168.1.2  -->  192x(256)^3 + 168x(256)^2 + 1x(256)^1 + 2x(256)^0 = 3232235778
        String[] addressParts = canonicalIp.split("\\.");

        long result = 0;
        for (int i = 0; i < addressParts.length; i++) {
            int power = 3 - i;
            int ip = Integer.parseInt(addressParts[i]);
            result += ip * Math.pow(256, power);
        }

        return result;
    }

    public String decimalToCanonicalIp(long ip) {
        return ((ip >> 24) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "."
                + (ip & 0xFF);

    }

}
