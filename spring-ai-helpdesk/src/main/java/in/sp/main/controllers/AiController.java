package in.sp.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sp.main.service.AiService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class AiController {
	
	@Autowired
	private AiService aiService;
	
	@PostMapping("/sync") // We will send the queries using raw texts as POST requests
	public ResponseEntity<String> chatSyncWithAi(
			@RequestBody String query, 
			@RequestHeader String email // we will use it as conversation_id
			) {
		String responseFromAssistant = aiService.getSyncResponseFromAssistant(query, email);
		
		return ResponseEntity.ofNullable(responseFromAssistant);
	}
	
	@PostMapping("/stream") // We will send the queries using raw texts as POST requests
	public ResponseEntity<Flux<String>> chatStreamWithAi(
			@RequestBody String query, 
			@RequestHeader String email // we will use it as conversation_id
			) {
		Flux<String> responseFromAssistant = aiService.getStreamResponseFromAssistant(query, email);
		
		return ResponseEntity.ofNullable(responseFromAssistant);
	}

}
