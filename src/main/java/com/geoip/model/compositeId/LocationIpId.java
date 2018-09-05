package com.geoip.model.compositeId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geoip.model.LocationIp;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
@Data
public class LocationIpId implements Serializable {

    @JsonIgnore
    @Column(name = "ip_from")
    @Min(0)
    @DecimalMax("4294967295")
    private long ipFrom;


    @JsonIgnore
    @Column(name = "ip_to")
    @Min(0)
    @DecimalMax("4294967295")
    private long ipTo;


}
