package com.fishingLog;

import com.fishingLog.spring.service.integration.BaseIntegrationIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FishingLogApplicationTests extends BaseIntegrationIntegrationTest {

	@Test
	void contextLoads() {
	}

}
