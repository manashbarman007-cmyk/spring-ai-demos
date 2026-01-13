package in.sp.main.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AiConfig {
	
//	@Autowired
//	private ChatMemory chatMemory;	// by default it uses InMemoryChatMemoryRepository and MessageWindowChatMemory 	
	
	@Autowired
	private JdbcChatMemoryRepository jdbcChatMemoryRepository;
	@Bean
	public ChatClient chatClientConfig (ChatClient.Builder builder) {
		
		ChatMemory chatMemory= MessageWindowChatMemory
				.builder()
				.chatMemoryRepository(jdbcChatMemoryRepository)
				.maxMessages(15) // default is 20
				.build();
		
		return builder
				.defaultSystem("Summarize the response in about 400 words")
				.defaultAdvisors(
						SimpleLoggerAdvisor.builder().build(),
						MessageChatMemoryAdvisor.builder(chatMemory).build()
					)
				.build();
	}

}
