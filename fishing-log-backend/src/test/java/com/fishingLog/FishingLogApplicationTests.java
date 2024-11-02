package com.fishingLog;

import com.fishingLog.spring.service.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FishingLogApplicationTests extends BaseIntegrationTest {

	@Test
	void contextLoads() {
	}

}
