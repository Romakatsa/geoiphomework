package com.geoip.controller;

import com.geoip.model.LocationIp;
import com.geoip.service.LocationIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Pattern;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/geoip")
public class LocationIpController {

    private final LocationIpService locationIpService;

    @Autowired
    public LocationIpController(LocationIpService locationIpService) {
        this.locationIpService = locationIpService;
    }


    @GetMapping("/{ip}")
    public LocationIp getIpLocation(@PathVariable(value = "ip") String ip) {
        return locationIpService.findLocationByIp(ip);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleValidationExceptions(IllegalArgumentException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleValidationExceptions(NoSuchElementException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

}

