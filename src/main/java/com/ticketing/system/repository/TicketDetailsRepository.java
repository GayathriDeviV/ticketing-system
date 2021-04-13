package com.ticketing.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ticketing.system.model.TicketDetails;

@Repository
public interface TicketDetailsRepository extends CrudRepository<TicketDetails, Integer> {
	List<TicketDetails> findAll();

	List<TicketDetails> findAllByStatus(String status);

	List<TicketDetails> findAllByAssignee(String assignee);

	List<TicketDetails> findAllByCustomer(String customer);

	@Query(value = "select * from ticket_details where status = 's_04' and datediff(Date(now()),updated_at) > 30", nativeQuery = true)
	List<TicketDetails> findResolvedTickets();

	@Query(value = "select ticket_id from ticket_details where status = 's_01' and assignee is NULL limit 1", nativeQuery = true)
	int getNewStatusTicket();

	@Query(value = "update ticket_details set assignee= ?1 where ticket_id= ?2", nativeQuery = true)
	TicketDetails assignedNewTicket(String assignee, int ticketID);
}
