package com.ticketing.system.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ticketing.system.model.ResponseMaster;
import com.ticketing.system.model.TicketDetails;

public interface TSServices {

	ResponseEntity<TicketDetails> createTicket(TicketDetails ticketDetails);

	List<TicketDetails> getAll();

	List<TicketDetails> getTicketByAssignee(String assignee);

	List<TicketDetails> getTicketByCustomer(String customer);

	List<TicketDetails> getTicketByStatus(String status);

	ResponseEntity<TicketDetails> getTicketById(int ticketId);

	ResponseEntity<TicketDetails> updateTicket(int ticketId, TicketDetails ticketDetails);

	ResponseEntity<TicketDetails> updateStatus(int ticketId, String status);

	ResponseEntity<TicketDetails> updateAssignee(int ticketId, String assignee);

	ResponseEntity<ResponseMaster> createResponse(ResponseMaster responseDetails);
	
	ResponseEntity<String> deleteTicket(int ticketId);

	ResponseEntity<ResponseMaster> updateResponse(int ticketId, ResponseMaster responseDetails) throws IOException;

	ResponseEntity<String> deleteResponse(int ticketId, int responseId);

	List<TicketDetails> getResolvedTickets();

}
