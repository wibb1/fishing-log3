package com.fishingLog.spring.service;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

@SpringJUnitConfig
@Tag("integration")
public abstract class BaseIntegrationIntegrationTest {
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withCreateContainerCmdModifier(cmd -> {
                cmd.getHostConfig().withNetworkMode("bridge");
                cmd.getHostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(5433), ExposedPort.tcp(5432)));
            })
            .waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
            .withReuse(true)
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(BaseIntegrationIntegrationTest.class)));

    @BeforeAll
    static void setup() {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start();
        }
    }

    @AfterAll
    static void tearDown() {
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.stop();
        }
    }

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}
