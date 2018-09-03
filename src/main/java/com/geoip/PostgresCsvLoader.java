package com.geoip;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;

/**
 * Created by Roma on 03.09.2018.
 */

@ConditionalOnProperty(prefix = "customProps", name = {"pathToCsv","tableNameCsvColumnsOrder","populateDBFromCSV"})
@Component
public class PostgresCsvLoader implements ApplicationRunner {

    @Value("${customProps.pathToCsv}")
    private String pathToCsv;

    @Value("${customProps.tableNameCsvColumnsOrder}")
    private String tableNameCsvColumnsOrder;

    private DataSource dataSource;

    private static final Logger logger = LoggerFactory.getLogger(PostgresCsvLoader.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        //could not autowire error message in IDEA 2016.3.1 but compiles and at runtime is ok
        this.dataSource = dataSource;
    }

    public void run(ApplicationArguments args) {

        final String sql = "COPY " + tableNameCsvColumnsOrder +" FROM stdin (FORMAT csv, DELIMITER ',', QUOTE '\"', NULL 'null')";
        System.out.println(sql);
        try {
            Long stTimeInSec = Instant.now().getEpochSecond();

            logger.info("Try populate database...");
            Connection con = DataSourceUtils.getConnection(dataSource);
            PGConnection pgCon=con.unwrap(PGConnection.class);
            CopyManager mgr = pgCon.getCopyAPI();
            ClassPathResource cpr = new ClassPathResource(pathToCsv);
            long rowsAffected = mgr.copyIn(sql,cpr.getInputStream());
            System.out.println("Rows copied: " + rowsAffected);

            Long endTimeInSec = Instant.now().getEpochSecond();
            Long elapsedTime = endTimeInSec - stTimeInSec;
            logger.info("elapsed db population time is {}",elapsedTime);

        } catch (SQLException | IOException e1) {
            e1.printStackTrace();
        }

    }


}
