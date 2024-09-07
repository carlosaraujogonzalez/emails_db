package com.gbtec.microdb.entities;

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
@Table(name = "emailscc")
public class EmailsCc {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="emailsccid")
    private Integer emailsccid;
    
    @Column(name="email")
    private String email;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emailid", nullable = false)
    private Email mail;
}