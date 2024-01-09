package com.blooddonation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.profile.active=test")
class ProjectApplicationTests {

		@Test
		void contextLoads() {
			// 테스트
		}
}
