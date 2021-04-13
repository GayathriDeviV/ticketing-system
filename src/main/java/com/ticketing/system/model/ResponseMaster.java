package com.ticketing.system.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "response_master")
@NamedQuery(name = "ResponseMaster.deleteByTicketIdAndResponseId", query = "delete from ResponseMaster r where r.ticketId = ?1 and r.responseId = ?2")
public class ResponseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "response_id")
	private Integer responseId;
	@Column(name = "ticket_id")
	private Integer ticketId;
	private String response;
	private String created_by;
	@CreationTimestamp
	private Timestamp created_at;
	private String updated_by;
	@CreationTimestamp
	private Timestamp updated_at;

}
