package com.ticketing.system.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ticketing.system.service.TSServices;

/**
 * @author gayathri
 *
 */

/**
 * TicketScheduler updates the ticket marked as Resolved status 30 days ago to
 * closed status For convenience,this Scheduler has been added in the same project
 * Recommended practise is to have scheduler in different project and run it as
 * an independent scheduler/batch
 *
 */
@Component
public class TicketScheduler {

	@Autowired
	TSServices TSServices;

	private Logger logger = LoggerFactory.getLogger(TicketScheduler.class);

	@Scheduled(cron = "0 16 2 * * ?")
	public void changeResolvedToClosed() {
		logger.info("scheduler Started");
		TSServices.getResolvedTickets();
		logger.info("scheduler Ended");
	}
}
