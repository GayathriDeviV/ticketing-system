package com.ticketing.system.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ticketing.system.model.ResponseMaster;

@Repository
public interface ResponseMasterRepository extends CrudRepository<ResponseMaster, String> {

	Optional<ResponseMaster> findByTicketIdAndResponseId(int ticketId, Integer integer);

	@Transactional
	@Modifying
	void deleteByTicketIdAndResponseId(int ticketId, int responseId);
}
