package com.odk.entity;

import com.odk.enums.TypeRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="personne")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String prenom;
    @Column(unique = true)
    private String email;
    private String mdp;
    private TypeRole role;

}
