package in.sp.main.tools;

import java.time.LocalDateTime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SimpleDateTimeTool {
	
	@Tool(description = "get the current date and time in the user's time zone")
	public String getCurrentDateTime() {
		
		return LocalDateTime.now().atZone(
				LocaleContextHolder.getTimeZone().toZoneId()
				).toString();
				
		
	}

}
