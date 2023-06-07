package com.example.DL.dto;

import com.example.DL.models.Groupe;

import java.util.ArrayList;
import java.util.List;

public class GroupeMapper {
    public static GroupeDTO mapToGroupeDTO(Groupe groupe){
        if(groupe == null) return null;
        return GroupeDTO.builder()
                .id(groupe.getId())
                .nom(groupe.getNom())
                .contactList(ContactMapper.mapAllToGroupeSummaryDTO(groupe.getContacts()))
                .build();
    }

    public static List<GroupeDTO> mapAllToGroupeDTO(List<Groupe> groupes) {
        if(groupes == null) return null;
        List<GroupeDTO> groupesDTO = new ArrayList<>(groupes.size());
        groupes.forEach(groupe -> {
            groupesDTO.add(mapToGroupeDTO(groupe));
        });
        return groupesDTO;
    }
    public static Groupe mapToGroupe(GroupeRequest request){
        return Groupe.builder()
                .nom(request.getNom())
                .build();
    }
}
