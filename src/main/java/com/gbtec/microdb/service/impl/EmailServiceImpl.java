package com.gbtec.microdb.service.impl;

import com.gbtec.microdb.entities.Email;
import com.gbtec.microdb.entities.EmailsCc;
import com.gbtec.microdb.entities.EmailsTo;
import com.gbtec.microdb.exceptions.EmailExistsException;
import com.gbtec.microdb.exceptions.UpdateFailedException;
import com.gbtec.microdb.model.dto.EmailDto;
import com.gbtec.microdb.model.dto.EmailStringDto;
import com.gbtec.microdb.model.dto.EmailsDto;
import com.gbtec.microdb.repositories.EmailCcRepository;
import com.gbtec.microdb.repositories.EmailRepository;
import com.gbtec.microdb.repositories.EmailToRepository;
import com.gbtec.microdb.service.EmailService;
import com.gbtec.microdb.utils.Constants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailRepository emailRepository;
    
    @Autowired
    private EmailCcRepository emailccRepository;
    
    @Autowired
    private EmailToRepository emailtoRepository;


    @Override
    public void addEmails(EmailsDto emails) throws EmailExistsException {
    	
        log.info("addEmails - emails: " + emails);
        
        emails.getEmails().forEach(email -> {
        	
        	var existingEmail = emailRepository.findById(email.getEmailId());
        	if ( existingEmail.isPresent()) {

        		throw new EmailExistsException("The email cannot be saved");
        	}
        	
        	var emaildao = getEmailDaoByEmailDtoAndEmailId(email, email.getEmailId());
        	
        	log.info("addEmails - saving email: " + emaildao);
        	emailRepository.save(emaildao);
        	log.info("addEmails - saved");
        	
        	updateEmailCCByEmailAndEmailDto( email, emaildao);
        	
        	updateEmailToByEmailAndEmailDto( email, emaildao);

        });
       
    }
    
    @Override
    public EmailsDto getEmails() {

        log.info("getEmails - findAll");
    	var listemailsdto = new ArrayList<EmailDto>();
    	var emails = emailRepository.findAll();
    	
    	log.info("getEmails - getting emailscc");
    	emails.forEach(email -> {
    		
    		var emaildto = getEmailDtoByEmail(email);
        	listemailsdto.add(emaildto);
    	});
    	var emailsdto = new EmailsDto();
    	emailsdto.setEmails(listemailsdto);
        return emailsdto;
        
    }

    @Override
    public void deleteEmails() {
    	
    	log.info("deleteEmails");
    	
    	var emails = emailRepository.findAll();
    	emails.forEach(email -> {
    		email.setState(Constants.EMAIL_STATE_ELIMINADO);
    		email.setLastUpdate(Calendar.getInstance().getTime());
    		emailRepository.save(email);
    	});
    	
    	log.info("deleteEmails -  deleted");
    }
    
    @Override
    public void updateEmail(EmailDto emaildto, int emailId) throws Exception {
    		
    	log.info("updateEmail - start: " + emaildto);
    	var email = emailRepository.findById(emailId);
    	if (!email.get().getState().equals(Constants.EMAIL_STATE_BORRADOR)) {
    		
    		throw new UpdateFailedException("The email cannot be updated");
    	}
    	
    	var emaildao = getEmailDaoByEmailDtoAndEmailId(emaildto, emailId);
    	
    	var emailcctodelete = emailccRepository.findByMail(emaildao);
    	emailccRepository.deleteAll(emailcctodelete);
    	
    	var emailtotodelete = emailtoRepository.findByMail(emaildao);
    	emailtoRepository.deleteAll(emailtotodelete);
    	
    	updateEmailCCByEmailAndEmailDto( emaildto, emaildao);
    	
    	updateEmailToByEmailAndEmailDto( emaildto, emaildao);
    	
    	emailRepository.save(emaildao);
    	
    	log.info("updateEmail - start: " + emaildto);
    }

    @Override
    public void deleteEmailById(int emailId) {
    	
    	log.info("deleteEmailById: " + emailId);
    	
    	var email = emailRepository.findById(emailId);
    	email.get().setState(Constants.EMAIL_STATE_ELIMINADO);
    	email.get().setLastUpdate(Calendar.getInstance().getTime());
    	emailRepository.save(email.get());
    	
    	log.info("deleteEmailById -  deleted");
    }
    

	@Override
	public EmailDto findEmailById(int emailId) {
		
		log.info("findEmailById: " + emailId);
		
		var email = emailRepository.findById(emailId);
		log.info("findEmailById: " + email.get());
		
		var emaildto = getEmailDtoByEmail(email.get());
		log.info("findEmailById: " + emaildto);
		
		log.info("findEmailById: " + email);
		return emaildto;
	}
	
	public Email getEmailDaoByEmailDtoAndEmailId(EmailDto email, int emailId) {
		
		log.info("getEmailDaoByEmailDtoAndEmailId: " + email + ", emailId: " + emailId);
		var emaildao = new Email();
		emaildao.setEmailBody(email.getEmailBody());
		emaildao.setEmailFrom(email.getEmailFrom());
		emaildao.setState(email.getState());
		emaildao.setEmailId(emailId);
		emaildao.setLastUpdate(Calendar.getInstance().getTime());
		log.info("getEmailDaoByEmailDtoAndEmailId" + emaildao);
		return emaildao;
	}

	public EmailDto getEmailDtoByEmail(Email email) {
		
		log.info("getEmailDtoByEmail - start: " + email);
		
		var emaildto = new EmailDto();
		emaildto.setEmailBody(email.getEmailBody());
		emaildto.setEmailFrom(email.getEmailFrom());
		emaildto.setEmailId(email.getEmailId());
		emaildto.setState(email.getState());
		
		var emailscc = emailccRepository.findByMail(email);	
		emailscc.forEach(emailcc -> {
			var emailstringdto = new EmailStringDto();
			emailstringdto.setEmail(emailcc.getEmail());
    		emaildto.getEmailCC().add(emailstringdto);
    	});
		
    	var emailsto = emailtoRepository.findByMail(email);
    	emailsto.forEach(emailto -> {
			var emailstringdto = new EmailStringDto();
			emailstringdto.setEmail(emailto.getEmail());
    		emaildto.getEmailTo().add(emailstringdto);	
    	});
    	
    	log.info("getEmailDtoByEmail - end: " + emaildto);
    	
    	return emaildto;
	}
	
	public void updateEmailCCByEmailAndEmailDto(EmailDto email, Email emaildao){
		
		log.info("updateEmailCCByEmailAndEmailDto");
		email.getEmailCC().forEach(emailcc -> {
			
			var emailccdao = new EmailsCc();
			emailccdao.setEmail(emailcc.getEmail());
			emailccdao.setMail(emaildao);
			
			log.info("updateEmailCCByEmailAndEmailDto - saving emailcc: " + emailccdao);
			emailccRepository.save(emailccdao);
			log.info("updateEmailCCByEmailAndEmailDto - saved");
		});
	}
	
	public void updateEmailToByEmailAndEmailDto(EmailDto email, Email emaildao){

		log.info("updateEmailToByEmailAndEmailDto");
		email.getEmailTo().forEach(emailto -> {
			
			var emailtodao = new EmailsTo();
			emailtodao.setEmail(emailto.getEmail());
			emailtodao.setMail(emaildao);
			
			log.info("updateEmailToByEmailAndEmailDto - saving emailto: " + emailtodao);
			emailtoRepository.save(emailtodao);
			log.info("updateEmailToByEmailAndEmailDto - saved");
		});
	}

	@Override
	public void markSpam(String emailfrom) {
		
		log.info("markSpam - Start");
		
		var listCarlEmails = emailRepository.findByEmailFrom(emailfrom);
		listCarlEmails.forEach(email -> {
			email.setState(Constants.EMAIL_STATE_SPAM);
			emailRepository.save(email);
		});
		
		log.info("markSpam - End");
	}

}
