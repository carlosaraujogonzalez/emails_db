package com.gbtec.microdb.repositories;

import com.gbtec.microdb.entities.Email;
import com.gbtec.microdb.entities.EmailsCc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCcRepository extends JpaRepository<EmailsCc, Integer>{

	List<EmailsCc> findByMail(Email mail);
}
