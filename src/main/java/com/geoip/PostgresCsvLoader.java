package com.geoip;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.PGConnection;
import org.postgresql.copy.CopyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;


@ConditionalOnProperty(prefix = "customProps", name = {"pathToCsv", "tableNameCsvColumnsOrder", "populateDBFromCSV"})
@Component
@Slf4j
public class PostgresCsvLoader implements ApplicationRunner {

    @Value("${customProps.pathToCsv}")
    private String pathToCsv;

    @Value("${customProps.tableNameCsvColumnsOrder}")
    private String tableNameCsvColumnsOrder;

    private final DataSource dataSource;


    @Autowired
    public PostgresCsvLoader(DataSource dataSource) {
        //could not autowire error message in IDEA 2016.3.1 but compiles and at runtime is ok
        this.dataSource = dataSource;
    }


    public void run(ApplicationArguments args) {

        final String sql = "COPY " + tableNameCsvColumnsOrder + " FROM stdin (FORMAT csv, DELIMITER ',', QUOTE '\"', NULL 'null')";

        try {

            Long stTimeInSec = Instant.now().getEpochSecond();
            log.info("Start populating database from csv...");

            // try get unwrapped plain postgres connection from dataSource
            // to perform copy from csv
            Connection con = DataSourceUtils.getConnection(dataSource);
            PGConnection pgCon = con.unwrap(PGConnection.class);
            CopyManager mgr = pgCon.getCopyAPI();
            ClassPathResource cpr = new ClassPathResource(pathToCsv);

            long rowsAffected = mgr.copyIn(sql, cpr.getInputStream());
            Long endTimeInSec = Instant.now().getEpochSecond();
            Long elapsedTime = endTimeInSec - stTimeInSec;

            log.info("Rows copied: " + rowsAffected + " in " + elapsedTime + " seconds");

        } catch (SQLException | IOException e1) {
            e1.printStackTrace();
            log.error("Failed to populate database from csv");
        }
    }

}
