package in.sp.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
	
	@Bean
	public RestClient configRestTemplate() {
		return RestClient
				.builder()
				.baseUrl("http://api.weatherapi.com/v1")
				.build();
	}

}
