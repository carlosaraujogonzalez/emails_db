package com.gbtec.microdb.repositories;

import com.gbtec.microdb.entities.Email;
import com.gbtec.microdb.entities.EmailsTo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface EmailToRepository extends JpaRepository<EmailsTo, Integer>{
	
	List<EmailsTo> findByMail(Email mail);
}
