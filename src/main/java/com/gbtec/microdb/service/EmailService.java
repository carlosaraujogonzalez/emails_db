package com.gbtec.microdb.service;

import com.gbtec.microdb.model.dto.EmailDto;
import com.gbtec.microdb.model.dto.EmailsDto;

public interface EmailService {
	
    void addEmails(EmailsDto emails) throws Exception;
    
    EmailsDto getEmails();
    
    void deleteEmails();
    
    EmailDto findEmailById(int emailId);
    
    void updateEmail(EmailDto email, int emailId) throws Exception;
    
    void deleteEmailById(int emailId);
    
    void markSpam(String email);
}
