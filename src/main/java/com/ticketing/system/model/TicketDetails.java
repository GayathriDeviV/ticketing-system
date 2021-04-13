package com.ticketing.system.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_details")

public class TicketDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ticket_id;

	private String ticket_type;
	private String title;
	private String description;
	private String customer;
	private String priority;
	private String status;
	private String assignee;
	private String created_by;
	@CreationTimestamp
	private Timestamp created_at;
	private String updated_by;
	@CreationTimestamp
	private Timestamp updated_at;
}
