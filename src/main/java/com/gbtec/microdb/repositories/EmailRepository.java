package com.gbtec.microdb.repositories;

import com.gbtec.microdb.entities.Email;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Integer>{

	List<Email> findByEmailFrom(String emailFrom);
}
