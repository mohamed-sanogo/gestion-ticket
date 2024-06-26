package com.odk.entity;

import com.odk.enums.TypeCategorie;
import com.odk.enums.TypePriorite;
import com.odk.enums.TypeStatut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private Integer id;
    private String titre;
    private String description;
    private TypeCategorie categorie;
    private TypePriorite priorite;
    private TypeStatut statut;
    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "apprenant_id")
    private Personne apprenant;
}
