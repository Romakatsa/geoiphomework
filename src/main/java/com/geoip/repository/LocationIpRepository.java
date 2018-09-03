package com.geoip.repository;

import com.geoip.model.LocationIp;
import com.geoip.model.compositeId.LocationIpId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Roma on 01.09.2018.
 */
@Repository
public interface LocationIpRepository extends JpaRepository<LocationIp, LocationIpId> {

    @Query(value = "SELECT * FROM ip_location WHERE int8range(ip_from-1,ip_to+1) @> int8(:ip)", nativeQuery = true)
    LocationIp findByIdIpFromLessThanEqualAndIdIpToGreaterThanEqual(@Param("ip") long ip1);

}
