package com.gbtec.microdb.model.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDto {
	
	private Integer emailId;
    
    private String emailFrom;
    
    private List<EmailStringDto> emailTo;
    
    private List<EmailStringDto> emailCC;
    
    private String emailBody;
    
    private Integer state;
    
    public EmailDto() {
    	this.emailTo = new ArrayList<>();
    	this.emailCC = new ArrayList<>();
    }
}
