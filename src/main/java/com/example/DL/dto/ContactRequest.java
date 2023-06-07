package com.example.DL.dto;

import com.example.DL.models.Contact;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    private Long id;

    @NotEmpty(message = "nom is null")
    @NotNull
    private String nom;

    @NotEmpty(message = "prenom is null")
    @NotNull
    private String prenom;

    @NotNull
    @NotEmpty
    private String numTele1;
    private String numTele2;
    private String adresse;
    private String email;
    private String emailPro;

    @NotNull
    private Contact.Genre genre;
}
