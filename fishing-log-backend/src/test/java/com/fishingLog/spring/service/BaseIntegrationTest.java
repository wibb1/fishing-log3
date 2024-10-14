package com.fishingLog.spring.service;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringJUnitConfig
public abstract class BaseIntegrationTest {
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withCreateContainerCmdModifier(cmd -> {
                cmd.getHostConfig().withNetworkMode("bridge");
                cmd.getHostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5433), ExposedPort.tcp(5432)));
            });

    @BeforeAll
    static void setup() {
        postgreSQLContainer.start();
    }
    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }
    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:" + postgreSQLContainer.getMappedPort(5432) + "/testdb");
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

}
