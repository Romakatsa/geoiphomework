package com.geoip.repository;

import com.geoip.model.LocationIp;
import com.geoip.model.compositeId.LocationIpId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Roma on 01.09.2018.
 */
@Repository
public interface LocationIpRepository extends JpaRepository<LocationIp, LocationIpId> {

}
