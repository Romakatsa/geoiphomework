package com.geoip.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geoip.model.compositeId.LocationIpId;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Data
@Table(name = "ip_location")
public class LocationIp {

    public static final String ipRegEx = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
    public static final String decPartRegEx = "^([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
    public static final String hexPartRegEx = "^[0][xX]0*[0-9a-fA-F]{1,2}$";
    public static final String octPartRegEx = "^[0]0*([1-7]|[1-7][0-7]|1[0-7]{2}|2[0-7][0-7]|3[0-7][0-7])$";

    public static final Long maxIp = 4294967295L;

    @Transient
    private long decimalIPv4;

    @JsonIgnore
    @EmbeddedId
    private LocationIpId id;

    @Transient
    @Pattern(regexp = ipRegEx)
    private String canonicalIPv4;

    @NotNull
    @Size(min = 1, max = 2)
    @Column(columnDefinition = "char(2) NOT NULL")
    private String countryCode;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(columnDefinition = "varchar(64) NOT NULL")
    private String countryName;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(columnDefinition = "varchar(128) NOT NULL")
    private String regionName;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(columnDefinition = "varchar(128) NOT NULL")
    private String cityName;

    @NotNull
    @Column(nullable = false)
    private double latitude;

    @NotNull
    @Column(nullable = false)
    private double longitude;


}






