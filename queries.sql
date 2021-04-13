
CREATE TABLE `priority_master` (
  `priority_id` varchar(20) NOT NULL,
  `priority` char(20) DEFAULT NULL,
  `created_by` char(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_by` char(50) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`priority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `response_master` (
  `ticket_id` int NOT NULL,
  `response_id` int NOT NULL AUTO_INCREMENT,
  `response` varchar(500) NOT NULL,
  `created_by` char(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_by` char(50) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`response_id`),
  KEY `ticket_id_response_idx` (`ticket_id`),
  CONSTRAINT `ticket_id` FOREIGN KEY (`ticket_id`) REFERENCES `ticket_details` (`ticket_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `status_master` (
  `status_id` varchar(20) NOT NULL,
  `status` char(20) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `created_by` char(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_by` char(50) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `ticket_type_master` (
  `ticket_type_id` varchar(20) NOT NULL,
  `ticket_type` char(20) DEFAULT NULL,
  `created_by` char(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_by` char(50) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ticket_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `ticket_details` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `ticket_type` varchar(20) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `customer` char(50) DEFAULT NULL,
  `priority` varchar(20) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `assignee` char(50) DEFAULT NULL,
  `created_by` char(50) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` char(50) DEFAULT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `priority_idx` (`priority`),
  KEY `type_idx` (`ticket_type`),
  KEY `status_idx` (`status`),
  CONSTRAINT `priority` FOREIGN KEY (`priority`) REFERENCES `priority_master` (`priority_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `status` FOREIGN KEY (`status`) REFERENCES `status_master` (`status_id`),
  CONSTRAINT `type` FOREIGN KEY (`ticket_type`) REFERENCES `ticket_type_master` (`ticket_type_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



