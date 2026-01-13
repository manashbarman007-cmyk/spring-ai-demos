package in.sp.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import in.sp.main.service.ChatServiceImpl;

@SpringBootTest
class OllamaAiDemoApplicationTests {
	
	@Autowired
	private ChatServiceImpl impl;

	@Test
	void contextLoads() {
		
		String chatTemplate = impl.chatTemplate("Tell me a joke", "123");
		System.out.println(chatTemplate);
	}

}
