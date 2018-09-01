package com.geoip.service;

import com.geoip.model.LocationIp;
import com.geoip.repository.LocationIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Roma on 01.09.2018.
 */
@Service
public class LocationIpService {

    @Autowired
    private LocationIpRepository locationIpRepository;


    public LocationIp findLocationByCanonicalIP(String canonicalIp) {

        long decimalIp = canonicalToDecimalIp(canonicalIp);
        return locationIpRepository.findByIdIpFromLessThanEqualAndIdIpToGreaterThanEqual(decimalIp,decimalIp);

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

}
