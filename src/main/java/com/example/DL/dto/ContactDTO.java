package com.example.DL.dto;

import com.example.DL.models.Contact;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter

public class ContactDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String emailPro;
    private String numTele1;
    private String numTele2;
    private String adresse;
    private List<GroupeDTO> groupes;
    private Contact.Genre genre;
}
