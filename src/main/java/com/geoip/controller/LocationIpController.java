package com.geoip.controller;

import com.geoip.model.LocationIp;
import com.geoip.repository.LocationIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Roma on 01.09.2018.
 */

@RestController
public class LocationIpController {

    @Autowired
    private LocationIpRepository locationIpRepository;

    @GetMapping("/geoip/{canonicalIp}")
    public LocationIp getIpLocation(@PathVariable String canonicalIp) {

        return new LocationIp();
    }
}

