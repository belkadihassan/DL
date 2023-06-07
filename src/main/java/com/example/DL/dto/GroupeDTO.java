package com.example.DL.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupeDTO {
    private Long id;
    private String nom;
    private List<ContactSummaryDTO> contactList;
}