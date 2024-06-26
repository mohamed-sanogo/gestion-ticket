package com.odk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "reponse")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String reponse;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "formateur_id")
    private Personne formateur;

}
