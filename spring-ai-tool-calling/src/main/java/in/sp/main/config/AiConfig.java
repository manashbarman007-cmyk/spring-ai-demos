package in.sp.main.config;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.sp.main.custom_advisor.TokenPrintAdvisor;

@Configuration
public class AiConfig {
	
	@Autowired
	private JdbcChatMemoryRepository jdbcChatMemoryRepository;

	@Bean
	public ChatClient chatClient(ChatClient.Builder builder) {

		ChatMemory chatMemory = MessageWindowChatMemory.builder()
				.chatMemoryRepository(jdbcChatMemoryRepository)
				.maxMessages(10) // will store last 10 messages in DB
				.build();
		

		return builder
				.defaultAdvisors(
						MessageChatMemoryAdvisor.builder(chatMemory).build(),
						new TokenPrintAdvisor(),
						SafeGuardAdvisor
						.builder()
						.sensitiveWords(List.of("confidential", "secret", "proprietary"))
						.failureResponse("This request has been blocked due to sensitive content policy violations.")
						.build())
				.build();
	}
}
