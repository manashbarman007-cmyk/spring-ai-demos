package in.sp.main.service;

import reactor.core.publisher.Flux;

public interface ChatService {
	
	String chatTemplate(String query, String userId);
	
	Flux<String> streamChatTemplate(String query, String userId);
	
	void addToVectorDb();
}
