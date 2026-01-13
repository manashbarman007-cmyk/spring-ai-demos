package in.sp.main.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/chat")
public class AiController {

	@Autowired
	private ChatClient chatClient;
	
	@PostMapping
	public ResponseEntity<String> chatResponse(@RequestBody String query) {
		
	    String content = chatClient
				.prompt()
				.system("You are a helpful assistant")
				.user(query)
				.call()
				.content();
	    return new ResponseEntity<String>(content, HttpStatus.OK);
	}
}
