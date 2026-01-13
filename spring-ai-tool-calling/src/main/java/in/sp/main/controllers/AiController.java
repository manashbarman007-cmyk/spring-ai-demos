package in.sp.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.sp.main.service.ChatServiceImpl;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class AiController {

	@Autowired
	private ChatServiceImpl impl;
	
	@GetMapping("/sync")
	public ResponseEntity<String> response(@RequestParam String query,
			@RequestHeader("userId") String userId) {
		String content = impl.chatTemplate(query, userId);
		return ResponseEntity.ok(content);
	}
	
	@GetMapping("/stream")
	public ResponseEntity<Flux<String>> streamResponse(@RequestParam String query,
			 @RequestHeader("userId") String userId) {

		Flux<String> content = impl.streamChatTemplate(query, userId);

		return ResponseEntity.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(content);
	}
	
	@PostMapping("/dump")
	public ResponseEntity<String> dumpInVectorDb() {
		impl.addToVectorDb();
		return ResponseEntity.ok("Documents dumped into vector DB");
	}
}
