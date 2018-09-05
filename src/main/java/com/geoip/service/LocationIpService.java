package com.geoip.service;

import com.geoip.PostgresCsvLoader;
import com.geoip.model.LocationIp;
import com.geoip.repository.LocationIpRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class LocationIpService {

    // patterns to test ip parts on.
    private final static Pattern decPartPattern = Pattern.compile(LocationIp.decPartRegEx);
    private final static Pattern hexPartPattern  = Pattern.compile(LocationIp.hexPartRegEx);
    private final static Pattern octPartPattern  = Pattern.compile(LocationIp.octPartRegEx);
    private final static long maxIp = LocationIp.maxIp;

    private final LocationIpRepository locationIpRepository;

    @Autowired
    public LocationIpService(LocationIpRepository locationIpRepository) {
        this.locationIpRepository = locationIpRepository;
    }

    public LocationIp findLocationByIp(String ip) {

        long startTimeMillis = System.currentTimeMillis();

        String canonicalIp;
        long decimalIp;

        if (!ip.contains(".")) {
            try {
                decimalIp = Long.decode(ip);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("String argument doesn't represent proper decimal ip address.");
            }
            if (decimalIp > maxIp) {
                throw new IllegalArgumentException("String argument doesn't represent proper decimal ip address. Too big number.");
            }
            canonicalIp = decimalToCanonicalIp(decimalIp);
        }
        else {

            String[] ipParts = ip.split("\\.");
            if(isValidIpParts(ipParts)) {

                int[] canonicalIpParts = getCanonicalIpParts(ipParts);
                decimalIp = canonicalToDecimalIp(canonicalIpParts);
                canonicalIp = canonicalIpParts[0]+"."
                                +canonicalIpParts[1]+"."
                                    +canonicalIpParts[2]+"."
                                        +canonicalIpParts[3];
            }
            else {
                throw new IllegalArgumentException("String argument doesn't represent proper ip address.");
            }
        }

        LocationIp locationIp = locationIpRepository.findByIp(decimalIp);
        if (locationIp != null) {
            locationIp.setCanonicalIPv4(canonicalIp);
            locationIp.setDecimalIPv4(decimalIp);
        } else {
            throw new NoSuchElementException("There are no information about requested IP ("+canonicalIp+")");
        }

        long endTimeMillis= System.currentTimeMillis();
        log.info("time fetching element from DB is {} ms", endTimeMillis - startTimeMillis);
        return locationIp;
    }



    private boolean isValidIpParts(String[] parts) {

        if (parts.length > 4) {
            return false;
        }
        for (String part: parts) {

            Matcher hexMatcher = hexPartPattern.matcher(part);
            if (hexMatcher.matches()) continue;
            Matcher octMatcher = octPartPattern.matcher(part);
            if (octMatcher.matches()) continue;
            Matcher decMatcher = decPartPattern.matcher(part);
            if (decMatcher.matches()) continue;

            return false;
        }

        return true;
    }

    /*
    converts ip address' parts to decimal numbers
    and makes ip canonical if some parts are missing
    inserting zero parts in proper places.

     */
    private int[] getCanonicalIpParts(String[] addressParts) {

        int totalParts = addressParts.length;
        if (totalParts == 1) {
            return new int[] {
                    0, 0, 0, Integer.decode(addressParts[0])
            };
        }
        if (totalParts == 2) {
            return new int[] {
                    Integer.decode(addressParts[0]),
                    0, 0, Integer.decode(addressParts[1])
            };
        }
        if (totalParts == 3) {
            return new int[] {
                    Integer.decode(addressParts[0]),
                    Integer.decode(addressParts[1]),
                    0, Integer.decode(addressParts[2])
            };
        }
        if (totalParts == 4) {
            return new int[] {
                    Integer.decode(addressParts[0]),
                    Integer.decode(addressParts[1]),
                    Integer.decode(addressParts[2]),
                    Integer.decode(addressParts[3])
            };
        }
        return new int[4];
    }


    private int getBaseOfNumber(String number) {

        if (number.startsWith("0x") || number.startsWith("0X"))
            return 16;
        if (number.startsWith("0"))
            return 8;

        return 10;
    }

    private long canonicalToDecimalIp(int[] parts) {

        //Uses following conversion logic:
        //192.168.1.2  -->  192x(256)^3 + 168x(256)^2 + 1x(256)^1 + 2x(256)^0 = 3232235778

        int totalParts = parts.length;
        long result = 0;

        for (int i = 0; i < totalParts; i++) {
            result += parts[totalParts-i-1] * Math.pow(256, i);
        }
        return result;
    }


    private String decimalToCanonicalIp(long ip) {
        return ((ip >> 24) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "."
                + (ip & 0xFF);

    }

}
