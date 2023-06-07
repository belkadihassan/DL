package com.example.DL.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String numTele1;
    private String numTele2;
    private String adresse;
    private String email;
    private String emailPro;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToMany
    private List<Groupe> groupes;
    public enum Genre{
        MALE,
        FEMALE
    }
}
