package in.sp.main.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import in.sp.main.tools.TicketTools;
import reactor.core.publisher.Flux;


@Service
public class AiService {
	
	@Value("classpath:prompts/helpdesk-system.st")
	private Resource systemResource;
	
	@Autowired
	private ChatClient chatClient;
	
	@Autowired
	private TicketTools tools;
	
	public String getSyncResponseFromAssistant(String userquery, String email) {
		return chatClient
				.prompt()
				.system(systemResource)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, email)) // we will use the email as 
				                                                           // conversation_id
				.user(userquery)
				.tools(tools)
				.call()
				.content();
	}
	
	public Flux<String> getStreamResponseFromAssistant(String userquery, String email) {
		return chatClient
				.prompt()
				.system(systemResource)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, email)) // we will use the email as 
				// conversation_id
				.user(userquery)
				.tools(tools)
				.stream()
				.content();
	}

}
