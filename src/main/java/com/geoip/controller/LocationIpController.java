package com.geoip.controller;

import com.geoip.model.LocationIp;
import com.geoip.repository.LocationIpRepository;
import com.geoip.service.LocationIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * Created by Roma on 01.09.2018.
 */

@RestController
@Validated
@RequestMapping("/geoip")
public class LocationIpController {


    @Autowired
    private LocationIpService locationIpService;


    @GetMapping("/{canonicalIp}")
    public LocationIp getIpLocation(@PathVariable @Valid @Pattern(regexp = LocationIp.ipRegEx, message = "{ipValidationMsg}") String canonicalIp) {

        return locationIpService.findLocationByCanonicalIP(canonicalIp);
        //return new LocationIp();
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleValidationExceptions(ConstraintViolationException ex) {

        System.out.println();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

}

