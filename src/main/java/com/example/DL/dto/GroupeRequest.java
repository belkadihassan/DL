package com.example.DL.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupeRequest {

    @NotEmpty
    @NotNull
    private String nom;
}
