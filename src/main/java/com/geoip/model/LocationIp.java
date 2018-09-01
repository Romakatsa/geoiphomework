package com.geoip.model;

import com.geoip.model.compositeId.LocationIpId;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Roma on 01.09.2018.
 */
@Entity
@Data
@Table(name="ip_location")
public class LocationIp {

    @EmbeddedId
    private LocationIpId id;

    @Column(columnDefinition = "char(2) NOT NULL")
    private String countryCode;

    @Column(columnDefinition = "varchar(64) NOT NULL")
    private String countryName;

    @Column(columnDefinition = "varchar(128) NOT NULL")
    private String regionName;

    @Column(columnDefinition = "varchar(128) NOT NULL")
    private String cityName;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;


}






