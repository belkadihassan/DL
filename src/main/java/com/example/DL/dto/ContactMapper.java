package com.example.DL.dto;

import com.example.DL.models.Contact;

import java.util.ArrayList;
import java.util.List;


public class ContactMapper {
    public static ContactDTO mapToContactDTO(Contact contact){
        if (contact == null) return null;
        return ContactDTO.builder()
                .id(contact.getId())
                .nom(contact.getNom())
                .prenom(contact.getPrenom())
                .email(contact.getEmail())
                .emailPro(contact.getEmailPro())
                .numTele1(contact.getNumTele1())
                .numTele2(contact.getNumTele2())
                .adresse(contact.getAdresse())
                .genre(contact.getGenre())
                .groupes(GroupeMapper.mapAllToGroupeDTO(contact.getGroupes()))
                .build();
    }
    public static ContactSummaryDTO mapToContactSummaryDTO(Contact contact){
        if (contact == null) return null;
        return ContactSummaryDTO.builder()
                .id(contact.getId())
                .nom(contact.getNom())
                .prenom(contact.getPrenom())
                .adresse(contact.getAdresse())
                .numTele1(contact.getNumTele1())
                .numTele2(contact.getNumTele2())
                .genre(contact.getGenre())
                .email(contact.getEmail())
                .emailPro(contact.getEmailPro())
                .build();
    }
    public static Contact mapToContact(ContactRequest cq){
        return Contact.builder()
                .nom(cq.getNom())
                .prenom(cq.getPrenom())
                .adresse(cq.getAdresse())
                .email(cq.getEmail())
                .emailPro(cq.getEmailPro())
                .numTele1(cq.getNumTele1())
                .numTele2(cq.getNumTele2())
                .genre(cq.getGenre())
                .adresse(cq.getAdresse())
                .build();
    }


    public static List<ContactDTO> mapAllToContactDTO(List<Contact> contacts) {
        if(contacts == null) return null;
        List<ContactDTO> contactsDTO = new ArrayList<>(contacts.size());
        contacts.forEach(contact -> {
            contactsDTO.add(mapToContactDTO(contact));
        });
        return contactsDTO;
    }
    public static List<ContactSummaryDTO> mapAllToGroupeSummaryDTO(List<Contact> contacts) {
        if(contacts == null) return null;
        List<ContactSummaryDTO> contactSummaryDTO = new ArrayList<>(contacts.size());
        contacts.forEach(contact -> {
            contactSummaryDTO.add(mapToContactSummaryDTO(contact));
        });
        return contactSummaryDTO;
    }
}
