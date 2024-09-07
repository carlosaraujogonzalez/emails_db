package com.gbtec.microdb.entities;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "emailsto")
public class EmailsTo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emailstoSeqGen")
    @SequenceGenerator(name = "emailstoSeqGen", sequenceName = "emailsto_seq", allocationSize = 1)
    @Column(name="emailstoid")
    private Integer emailstoid;
    
    @Column(name="email")
    private String email;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "emailid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Email mail;
}