package com.geoip.model.compositeId;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Roma on 01.09.2018.
 */
@Embeddable
@Data
public class LocationIpId implements Serializable {

        @Column(name = "ip_from")
        private long ipFrom;

        @Column(name = "ip_to")
        private long ipTo;

        //TODO: override equals() and hashCode() for composite key


}
