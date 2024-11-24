package com.fishingLog.spring.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("integration")
public class PostgresContainerIntegrationTest extends BaseIntegrationIntegrationTest {

    @Test
    void testDatabaseConnection() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword())) {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public'");

            boolean anglerExists = false;
            boolean astrologicalExists = false;
            boolean recordExists = false;
            boolean speciesExists = false;
            boolean tideExists = false;
            boolean tideStationExists = false;
            boolean weatherExists = false;

            while (rs.next()) {
                String tableName = rs.getString(1);
                System.out.println("Found table: " + tableName);
                switch (tableName) {
                    case "angler" -> anglerExists = true;
                    case "astrological" -> astrologicalExists = true;
                    case "record" -> recordExists = true;
                    case "species" -> speciesExists = true;
                    case "tide" -> tideExists = true;
                    case "tide_station" -> tideStationExists = true;
                    case "weather" -> weatherExists = true;
                }
            }

            assertTrue(anglerExists, "Angler table should exist");
            assertTrue(astrologicalExists, "Astrological table should exist");
            assertTrue(recordExists, "Record table should exist");
            assertTrue(speciesExists, "Species table should exist");
            assertTrue(tideExists, "Tide table should exist");
            assertTrue(tideStationExists, "Tide Station table should exist");
            assertTrue(weatherExists, "Weather table should exist");
        }
    }
}
