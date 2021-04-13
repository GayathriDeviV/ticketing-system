package com.ticketing.system.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ticketing.system.model.ResponseMaster;
import com.ticketing.system.model.TicketDetails;
import com.ticketing.system.service.TSServices;
import com.ticketing.system.service.impl.TSServicesImpl;

/**
 * @author gayathri
 *
 */

@RestController
public class TSController {

	private Logger logger = LoggerFactory.getLogger(TSServicesImpl.class);

	@Autowired
	TSServices TSServices;

	/**
	 * createTicket method is used for creating a ticket
	 * 
	 * @param ticketDetails
	 * @return
	 */
	@PostMapping(value = "/tickets", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDetails> createTicket(@RequestBody TicketDetails ticketDetails) {
		return TSServices.createTicket(ticketDetails);
	}

	/**
	 * getAll method lists all the tickets
	 * 
	 * @return List<TicketDetails>
	 */
	@RequestMapping(value = "/tickets", method = RequestMethod.GET)
	public List<TicketDetails> getAll() {
		return TSServices.getAll();
	}

	/**
	 * getTicketByAssignee method is used for filtering tickets by assigned agent
	 * 
	 * @param assignee
	 * @return List<TicketDetails>
	 */
	@RequestMapping(value = "/tickets/assignee/{assignee}", method = RequestMethod.GET)
	public List<TicketDetails> getTicketByAssignee(@PathVariable("assignee") String assignee) {
		return TSServices.getTicketByAssignee(assignee);
	}

	/**
	 * getTicketByCustomer method is used for filtering tickets by customer
	 * 
	 * @param customer
	 * @return List<TicketDetails>
	 */

	@RequestMapping(value = "/tickets/customer/{customer}", method = RequestMethod.GET)
	public List<TicketDetails> getTicketByCustomer(@PathVariable("customer") String customer) {
		return TSServices.getTicketByCustomer(customer);
	}

	/**
	 * getTicketByStatus ethod is used for filtering tickets by status
	 * 
	 * @param status
	 * @return List<TicketDetails>
	 */
	@RequestMapping(value = "/tickets/status/{status}", method = RequestMethod.GET)
	public List<TicketDetails> getTicketByStatus(@PathVariable("status") String status) {
		return TSServices.getTicketByStatus(status);
	}

	/**
	 * getTicketById method is used for retrieving details of a given ticket
	 * 
	 * @param ticketId
	 * @return ResponseEntity<TicketDetails>
	 */

	@RequestMapping(value = "/tickets/{ticket_id}", method = RequestMethod.GET)
	public ResponseEntity<TicketDetails> getTicketById(@PathVariable("ticket_id") int ticketId) {
		return TSServices.getTicketById(ticketId);
	}

	/**
	 * updateTicket method is used for editing details about the ticket
	 * 
	 * @param ticketId
	 * @param ticketDetails
	 * @return ResponseEntity<TicketDetails>
	 */
	@RequestMapping(value = "/tickets/{ticket_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDetails> updateTicket(@PathVariable("ticket_id") int ticketId,
			@RequestBody TicketDetails ticketDetails) {
		return TSServices.updateTicket(ticketId, ticketDetails);
	}

	/**
	 * updateStatus method is used for updating status for the ticket
	 * 
	 * @param ticketId
	 * @param status
	 * @return ResponseEntity<TicketDetails>
	 */
	@RequestMapping(value = "/tickets/status/{ticket_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDetails> updateStatus(@PathVariable("ticket_id") int ticketId,
			@RequestBody String status) {
		return TSServices.updateStatus(ticketId, status);
	}

	/**
	 * updateAssignee method is used for assigning the ticket to an agent
	 * 
	 * @param ticketId
	 * @param assignee
	 * @return ResponseEntity<TicketDetails>
	 */

	@RequestMapping(value = "/tickets/assignee/{ticket_id}", method = RequestMethod.PUT, consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<TicketDetails> updateAssignee(@PathVariable("ticket_id") int ticketId,
			@RequestBody String assignee) {
		return TSServices.updateAssignee(ticketId, assignee);
	}

	/**
	 * createReponse method is used for adding response to the ticket
	 * 
	 * @param responseMaster
	 * @return ResponseEntity<ResponseMaster>
	 */
	@PostMapping(value = "/tickets/response", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMaster> createReponse(@RequestBody ResponseMaster responseMaster) {
		return TSServices.createResponse(responseMaster);
	}

	/**
	 * deleteTicket method is used for deleting the ticket
	 * 
	 * @param ticketId
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(value = "/tickets/{ticket_id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTicket(@PathVariable("ticket_id") int ticketId) {
		return TSServices.deleteTicket(ticketId);
	}

	/**
	 * updateResponse method is used for sending email to customer when agent adds a
	 * response
	 * 
	 * @param ticketId
	 * @param responseMaster
	 * @return ResponseEntity<ResponseMaster
	 * @throws IOException
	 */

	@RequestMapping(value = "/tickets/response/{ticket_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMaster> updateResponse(@PathVariable("ticket_id") int ticketId,
			@RequestBody ResponseMaster responseMaster) throws IOException {
		return TSServices.updateResponse(ticketId, responseMaster);
	}

	@RequestMapping(value = "/tickets/response/{ticket_id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteResponse(@PathVariable("ticket_id") int ticketId, @RequestBody int responseId) {
		return TSServices.deleteResponse(ticketId, responseId);
	}

	/**
	 * getResolvedTickets method is used for updating tickets that were marked as
	 * Resolved status 30 days ago as closed status
	 * 
	 * @return List<TicketDetails>
	 */
	@RequestMapping(value = "/tickets/resolved", method = RequestMethod.GET)
	public List<TicketDetails> getResolvedTickets() {
		return TSServices.getResolvedTickets();
	}

}
