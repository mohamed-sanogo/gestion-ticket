package com.odk.entity;

import com.odk.enums.TypeCategorie;
import com.odk.enums.TypePriorite;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idticket;
    private String nom;
    private String description;
    private TypeCategorie categorie;
    private TypePriorite priorite;
    private String statut;
    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "client_id")
    private Client client;
}
