package com.geoip.controller;

import com.geoip.model.LocationIp;
import com.geoip.repository.LocationIpRepository;
import com.geoip.service.LocationIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Roma on 01.09.2018.
 */

@RestController
@RequestMapping("/geoip")
public class LocationIpController {

    @Autowired
    private LocationIpRepository locationIpRepository;

    @Autowired
    private LocationIpService locationIpService;

    @GetMapping("")
    public List<LocationIp> getAllIpLocations() {

        return locationIpRepository.findAll();

    }


    @GetMapping("/{canonicalIp}")
    public LocationIp getIpLocation(@PathVariable String canonicalIp) {

        return locationIpService.findLocationByCanonicalIP(canonicalIp);
        //return new LocationIp();
    }




}

