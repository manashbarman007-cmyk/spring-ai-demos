package in.sp.main.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entity.Ticket;
import in.sp.main.service.TicketServiceImpl;

@Service
public class TicketTools {
	
	@Autowired
	private TicketServiceImpl impl;
	
	@Tool(description = "Create Ticket for a user")
	public String createTicketWithAi(
			@ToolParam(description = "The Ticket object to create the entry", required = true) Ticket ticket
			) {
		
		Ticket insertTicket = impl.insertTicket(ticket);
		
		return "Successfully created Ticket with details : " + insertTicket.toString();
	}
	
	@Tool(description = "Get Ticket for a user by email")
	public String getTicketByEmailWithAi(
			@ToolParam(description = "The email of the user", required = true)String email
			) {
		
		return impl.getTicketByEmail(email).toString();
	}
	
	@Tool(description = "Update Ticket for a user by email")
	public String updateTicketByEmailWithAi(
			@ToolParam(description = "The email of the user", required = true) String email,
			@ToolParam(description = "The updated Ticket object to create the entry", required = true) Ticket updatedTicket
			) {
		
		return impl.updateTicketByEmail(email, updatedTicket).toString();
	}
	
	@Tool(description = "Delete Ticket for a user by email")
	public String deleteTicketByEmailWithAi(
			@ToolParam(description = "The email of the user", required = true) String email
			) {
		impl.deleteTicketByEmail(email);
		return "Successfully deleted ticket for " + email;
	}

}
