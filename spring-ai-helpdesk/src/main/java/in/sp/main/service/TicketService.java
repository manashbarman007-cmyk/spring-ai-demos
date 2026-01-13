package in.sp.main.service;


import in.sp.main.entity.Ticket;

public interface TicketService {

	// Create ticket
	Ticket insertTicket(Ticket ticket);
	
	// Get ticket
	Ticket getTicketByEmail(String email);
		
	// Update ticket
	Ticket updateTicketByEmail(String email, Ticket updatedTicket);
	
	// Delete ticket
	void deleteTicketByEmail(String email);
}
