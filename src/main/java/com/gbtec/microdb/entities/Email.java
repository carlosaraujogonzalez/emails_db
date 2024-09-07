package com.gbtec.microdb.entities;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Generates getters, setters, toString, equals, and hashCode methods.
@NoArgsConstructor // Generates a no-args constructor.
@AllArgsConstructor // Generates a constructor with all arguments.
@Builder // Generates a builder pattern for creating instances.
@Table(name = "emails")
public class Email {

    @Id
    @Column(name="emailId")
    private Integer emailId;
    
    @Column(name="emailFrom")
    private String emailFrom;
    
    @Column(name="emailBody")
    private String emailBody;
    
    @Column(name="state")
    private Integer state;
    
    @Column(name="lastUpdate")
    private Date lastUpdate;
}