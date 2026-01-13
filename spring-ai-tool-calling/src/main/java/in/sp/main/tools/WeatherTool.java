package in.sp.main.tools;

import java.util.Map;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherTool {
	
	@Value("${weather.api.key}")
	private String apiKey;
	
	@Autowired
	private RestClient restClient;
		
	@Tool(description = "Get the weather forecast based on the city name")
	public String getWeather(@ToolParam(description = "Name of the city", required = true) String city) {
		Map<String, Object> response = restClient
				.get()
				.uri(
						builder -> builder
						.path("/current.json")
						.queryParam("key", apiKey)
						.queryParam("q", city)
						.queryParam("aqi", "yes")
						.build()
						
					)
				.retrieve()
				.body(new ParameterizedTypeReference<Map<String, Object>>() {});
		
		String responseString = response.toString();
		
	return responseString;
	
	}

}
