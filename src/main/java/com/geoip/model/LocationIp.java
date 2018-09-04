package com.geoip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.geoip.model.compositeId.LocationIpId;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Roma on 01.09.2018.
 */
@Entity
@Data
@Table(name="ip_location")
public class LocationIp {

    public static final String ipRegEx = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

    @Transient
    private long IPv4;

    @JsonIgnore
    @EmbeddedId
    private LocationIpId id;

    @Transient
    @Pattern(regexp = ipRegEx)
    private String ip;

    @NotNull
    @Size(min=1,max=2)
    @Column(columnDefinition = "char(2) NOT NULL")
    private String countryCode;

    @NotNull
    @Size(min=1,max=64)
    @Column(columnDefinition = "varchar(64) NOT NULL")
    private String countryName;

    @NotNull
    @Size(min=1,max=128)
    @Column(columnDefinition = "varchar(128) NOT NULL")
    private String regionName;

    @NotNull
    @Size(min=1,max=128)
    @Column(columnDefinition = "varchar(128) NOT NULL")
    private String cityName;

    @NotNull
    @Column(nullable = false)
    private double latitude;

    @NotNull
    @Column(nullable = false)
    private double longitude;


}






