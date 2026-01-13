package in.sp.main.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

	@Bean
	public ChatClient configChatClient(ChatClient.Builder builder, ToolCallbackProvider tools) {
		return builder
				.defaultAdvisors(new SimpleLoggerAdvisor())
				.defaultToolCallbacks(tools) // to use the tools provided by the mcp server
				.build();
	}
}
