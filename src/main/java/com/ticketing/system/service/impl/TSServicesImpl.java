package com.ticketing.system.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ticketing.system.model.ResponseMaster;
import com.ticketing.system.model.TicketDetails;
import com.ticketing.system.repository.ResponseMasterRepository;
import com.ticketing.system.repository.TicketDetailsRepository;
import com.ticketing.system.service.TSServices;

@Service
public class TSServicesImpl implements TSServices {

	private Logger logger = LoggerFactory.getLogger(TSServicesImpl.class);

	@Autowired
	private TicketDetailsRepository ticketDetailsRepository;

	@Autowired
	private ResponseMasterRepository responseMasterRepository;

	@Autowired
	private MailSender mailSender;

	@Override
	public List<TicketDetails> getAll() {
		List<TicketDetails> tickets = ticketDetailsRepository.findAll();
		return tickets;
	}

	@Override
	public ResponseEntity<TicketDetails> createTicket(TicketDetails ticketDetails) {
		try {
			TicketDetails _ticketDetails = ticketDetailsRepository.save(ticketDetails);
			return new ResponseEntity<>(_ticketDetails, HttpStatus.CREATED);
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<TicketDetails> getTicketById(int ticketId) {

		Optional<TicketDetails> ticketDetails = ticketDetailsRepository.findById(ticketId);
		if (ticketDetails.isPresent()) {
			return new ResponseEntity<>(ticketDetails.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<TicketDetails> getTicketByAssignee(String assignee) {

		return ticketDetailsRepository.findAllByAssignee(assignee);
	}

	@Override
	public List<TicketDetails> getTicketByCustomer(String customer) {

		return ticketDetailsRepository.findAllByCustomer(customer);
	}

	@Override
	public List<TicketDetails> getTicketByStatus(String status) {

		return ticketDetailsRepository.findAllByStatus(status);
	}

	@Override
	public ResponseEntity<String> deleteTicket(int ticketId) {
		try {
			ticketDetailsRepository.deleteById(ticketId);
			return new ResponseEntity<>("Delete Successful", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<TicketDetails> updateTicket(int ticketId, TicketDetails ticketDetails) {
		Optional<TicketDetails> ticketData = ticketDetailsRepository.findById(ticketId);

		if (ticketData.isPresent()) {
			TicketDetails ticket = ticketData.get();
			ticket.setTicket_type(Objects.isNull(ticketDetails.getTicket_type()) ? ticket.getTicket_type()
					: ticketDetails.getTicket_type());
			ticket.setTitle(Objects.isNull(ticketDetails.getTitle()) ? ticket.getTitle() : ticketDetails.getTitle());
			ticket.setDescription(Objects.isNull(ticketDetails.getDescription()) ? ticket.getDescription()
					: ticketDetails.getDescription());
			ticket.setCustomer(
					Objects.isNull(ticketDetails.getCustomer()) ? ticket.getCustomer() : ticketDetails.getCustomer());
			ticket.setPriority(
					Objects.isNull(ticketDetails.getPriority()) ? ticket.getPriority() : ticketDetails.getPriority());
			ticket.setStatus(
					Objects.isNull(ticketDetails.getStatus()) ? ticket.getStatus() : ticketDetails.getStatus());
			ticket.setAssignee(
					Objects.isNull(ticketDetails.getAssignee()) ? ticket.getAssignee() : ticketDetails.getAssignee());

			// assigning the ticket equally to available agents
			if (ticket.getStatus().equals("s_02") || ticket.getStatus().equals("s_04")
					|| ticket.getStatus().equals("s_05")) {
				assignNewTickets(ticket.getAssignee());
			}

			ticket.setCreated_by(Objects.isNull(ticketDetails.getCreated_by()) ? ticket.getCreated_by()
					: ticketDetails.getCreated_by()); // Need to check and remove
			ticket.setUpdated_by(Objects.isNull(ticketDetails.getUpdated_by()) ? ticket.getUpdated_by()
					: ticketDetails.getUpdated_by());
			return new ResponseEntity<>(ticketDetailsRepository.save(ticket), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<TicketDetails> updateStatus(int ticketId, String status) {
		Optional<TicketDetails> ticketData = ticketDetailsRepository.findById(ticketId);
		if (!(status.isEmpty())) {
			if (ticketData.isPresent()) {
				TicketDetails ticket = ticketData.get();
				ticket.setStatus(status);
				return new ResponseEntity<>(ticketDetailsRepository.save(ticket), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ResponseEntity<TicketDetails> updateAssignee(int ticketId, String assignee) {
		Optional<TicketDetails> ticketData = ticketDetailsRepository.findById(ticketId);
		if (!assignee.isEmpty()) {
			if (ticketData.isPresent()) {
				TicketDetails ticket = ticketData.get();
				ticket.setAssignee(assignee);
				return new ResponseEntity<>(ticketDetailsRepository.save(ticket), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ResponseEntity<ResponseMaster> createResponse(ResponseMaster responseMaster) {
		try {
			ResponseMaster responseDetails = responseMasterRepository.save(responseMaster);
			return new ResponseEntity<>(responseDetails, HttpStatus.CREATED);
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<ResponseMaster> updateResponse(int ticketId, ResponseMaster responseDetails)
			throws IOException {
		Optional<ResponseMaster> responseData = responseMasterRepository.findByTicketIdAndResponseId(ticketId,
				responseDetails.getResponseId());

		if (responseData.isPresent()) {
			ResponseMaster response = responseData.get();
			response.setResponse(Objects.isNull(responseDetails.getResponse()) ? response.getResponse()
					: responseDetails.getResponse());
			response.setUpdated_by(Objects.isNull(responseDetails.getUpdated_by()) ? response.getUpdated_by()
					: responseDetails.getUpdated_by());

			ResponseEntity<ResponseMaster> data = new ResponseEntity<>(responseMasterRepository.save(response),
					HttpStatus.OK);
			String message = "You received a response from " + responseDetails.getUpdated_by() + " and the response is "
					+ responseDetails.getResponse();
			String subject = "Ticket ID: " + ticketId + " has been updated ";
			//Email id should be maintained and retrieved from DB .For testing purpose hardcoded To address.
			String mailId="xxx@gmail.com";
			mailSender.sendNotification(subject, message,mailId);
			return data;
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<String> deleteResponse(int ticketId, int responseId) {
		try {
			responseMasterRepository.deleteByTicketIdAndResponseId(ticketId, responseId);
			return new ResponseEntity<>("Delete Successful", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public List<TicketDetails> getResolvedTickets() {
		return ticketDetailsRepository.findResolvedTickets().stream().map(mapper -> {
			mapper.setStatus("s_05");
			ticketDetailsRepository.save(mapper);
			return mapper;
		}).collect(Collectors.toList());

	}

	/**
	 * assignNewTickets method is used for assigning the ticket equally to available
	 * agents
	 * 
	 * @param assignee
	 * @return TicketDetails
	 */

	private TicketDetails assignNewTickets(String assignee) {
		int ticketId = ticketDetailsRepository.getNewStatusTicket();
		return ticketDetailsRepository.assignedNewTicket(assignee, ticketId);
	}
} // Class End Braces
