package com.geoip.model.compositeId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by Roma on 01.09.2018.
 */
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

        //TODO: override equals() and hashCode() for composite key


}
