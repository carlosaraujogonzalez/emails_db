package com.gbtec.microdb.web;

import com.gbtec.microdb.model.dto.EmailDto;
import com.gbtec.microdb.model.dto.EmailsDto;
import com.gbtec.microdb.service.EmailService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
@Slf4j
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping
	public ResponseEntity<Void> addEmails(@RequestBody EmailsDto request) throws Exception {

		log.info("addEmails - Start");

		log.info("request: " + request);
			
		emailService.addEmails(request);	
		
		log.info("addEmails - End");

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping
	public ResponseEntity<EmailsDto> getEmails() {

		log.info("getEmails - Start");

		var emails = emailService.getEmails();
		log.info("getEmails: " + emails);

		log.info("getEmails - End");

		return new ResponseEntity<>(emails, HttpStatus.OK);
	}
	

	
	@DeleteMapping
	public ResponseEntity<Void> deleteEmails() {

		log.info("deleteEmails - Start");
			
		emailService.deleteEmails();
		
		log.info("deleteEmails - End");

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{emailId}")
	public ResponseEntity<EmailDto> findEmailById(@PathVariable int emailId) {

		log.info("findEmailById - Start");

		var email = emailService.findEmailById( emailId);
		log.info("findEmailById - email: " + email);

		log.info("findEmailById - End");

		return new ResponseEntity<>(email, HttpStatus.OK);
	}
	
	@PutMapping("/{emailId}")
	public ResponseEntity<Object> updateEmailById(@PathVariable int emailId, @RequestBody EmailDto email) throws Exception {

		log.info("updateEmail - Start");
		
		emailService.updateEmail(email, emailId);	
		
		log.info("updateEmail - End");

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{emailId}")
	public ResponseEntity<Void> deleteEmailById(@PathVariable int emailId) {

		log.info("deleteEmailById - Start");
			
		emailService.deleteEmailById(emailId);
		
		log.info("deleteEmailById - End");

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/markSpam/{email}")
	public ResponseEntity<Void> markSpam(@PathVariable String email) throws Exception {

		log.info("markSpam - Start");
			
		emailService.markSpam(email);	
		
		log.info("markSpam - End");

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
