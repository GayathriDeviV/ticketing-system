package com.ticketing.system.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Component
public class MailSender {

	private Logger logger = LoggerFactory.getLogger(MailSender.class);

	public Mail buildMailParams(String subject, String msg,String mailId) {
		Email from = new Email("yogesh@sinecycle.com");
	
		Email to = new Email(mailId);
		Content content = new Content("text/plain", msg);
		Mail mail = new Mail(from, subject, to, content);

		return mail;
	}

	public void sendNotification(String subject, String msg ,String mailId) throws IOException {
		final Mail mailObject = buildMailParams(subject, msg, mailId);
		send(mailObject);
	}

	private void send(final Mail mail) throws IOException {
		final SendGrid sg = new SendGrid("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");

		final Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint("mail/send");
		request.setBody(mail.build());

		final Response response = sg.api(request);
		logger.info(String.valueOf(response.getStatusCode()));
		logger.info(response.getBody());

	}
}