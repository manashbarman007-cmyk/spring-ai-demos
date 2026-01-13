package in.sp.main.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import in.sp.main.tools.DatabaseTool;

@Configuration
public class ToolCallbackConfig {

	// we will expose the Tools provided by the MCP server to the client
	@Bean
	public List<ToolCallback> exposeToolsCallbacks(DatabaseTool tools) {
		return new ArrayList<>(Arrays.asList(ToolCallbacks.from(tools))); // mutable
	}
}
