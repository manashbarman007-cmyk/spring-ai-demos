package in.sp.main.custom_advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class TokenPrintAdvisor implements CallAdvisor, StreamAdvisor{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.getClass().getName();
	}

	@Override
	public int getOrder() { // this effects the order of execution of the advisor
		// TODO Auto-generated method stub
		return 0;
//		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest,
			StreamAdvisorChain streamAdvisorChain) {
		log.info("TokenPrintAdvisor is called");
		log.info("Request:" + chatClientRequest.prompt().getContents());
		Flux<ChatClientResponse> responseFlux = streamAdvisorChain.nextStream(chatClientRequest);
		return responseFlux
				.doOnSubscribe(s -> log.info("Subscribed"))
				.doOnNext(chatClientResponse -> {
					log.info("Received chunk : " + chatClientResponse.chatResponse().getResult().getOutput().getText());
					
					// metadata may only be available at the end
					if (chatClientResponse.chatResponse().getMetadata().getUsage()
							.getTotalTokens() != null) {
						log.info("Tokens consumed thus far : " + chatClientResponse.chatResponse().getMetadata().getUsage()
								.getTotalTokens());
					}
				})
				.doOnComplete(() -> log.info("Task Completed"));
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		// TODO Auto-generated method stub
		
		log.info("TokenPrintAdvisor is called");
		
		log.info("Request:" + chatClientRequest.prompt().getContents());
		
		ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest); // pass the request
		 // execution to the next advisor in chain (or the AI model if no more advisors are left)
		
		log.info("Response received from the model");
		
		log.info("Response : " + chatClientResponse.chatResponse().getResult().getOutput().getText());
		
		log.info("Total tokens consumed :" + chatClientResponse.chatResponse().getMetadata()
				.getUsage().getTotalTokens());
		
		return chatClientResponse;
	}


}
