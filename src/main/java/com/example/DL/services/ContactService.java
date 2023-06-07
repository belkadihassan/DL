package com.example.DL.services;

import com.example.DL.Exceptions.ContactNotFoundException;
import com.example.DL.dto.ContactDTO;
import com.example.DL.dto.ContactMapper;
import com.example.DL.dto.ContactRequest;
import com.example.DL.dto.GroupeDTO;
import com.example.DL.models.Contact;
import com.example.DL.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepositorie;

    private final GroupeService groupeService;

    @Autowired
    public ContactService(ContactRepository contactRepositorie, GroupeService groupeService) {
        this.contactRepositorie = contactRepositorie;
        this.groupeService = groupeService;
    }

    //  GET - methods
    public List<ContactDTO> getAllContacts(String name , String tele , String type){

        List<Contact> contacts;
        if(!name.isEmpty()){
            if(tele.isEmpty()) {
                System.out.println("ibla3");
                contacts = contactRepositorie.searchByName(name , Sort.by(Sort.Order.asc("nom")));
            } else if (type.equals("tele1")) {
                contacts = contactRepositorie.searchByNameAndTele1(name ,tele, Sort.by(Sort.Order.asc("nom")));
            } else if (type.equals("tele2")) {
                contacts = contactRepositorie.searchByNameAndTele2(name ,tele, Sort.by(Sort.Order.asc("nom")));
            }else {throw new RuntimeException("type not defined");}
        }
        else{
            if(tele.isEmpty()) {
                contacts = contactRepositorie.findAll(Sort.by(Sort.Order.asc("nom")));
            } else if (type.equals("tele1")) {
                contacts = contactRepositorie.searchByNumTele1(tele, Sort.by(Sort.Order.asc("nom")));
            } else if (type.equals("tele2")) {
                contacts = contactRepositorie.searchByNumTele2(tele, Sort.by(Sort.Order.asc("nom")));
            }else {throw new RuntimeException("type not defined");}
        }
        return ContactMapper.mapAllToContactDTO(contacts);
    }
    public ContactDTO getContactById(Long id){
        return ContactMapper.mapToContactDTO(contactRepositorie.findById(id).orElse(null));
    }
    //  POST - methods
    public void addContact(Contact contact){
        contactRepositorie.save(contact);
    }
    //  DELETE - methods
    public void deleteContactById(Long id){
        Contact contact = contactRepositorie.findById(id).orElseThrow();
        contact.getGroupes().forEach(c -> c.getContacts().remove(contact));
        contactRepositorie.deleteById(id);
    }
    //  PUT - methods
    public void updateContact(ContactRequest contactRequest) throws ContactNotFoundException {
        Contact contact = contactRepositorie.findById(contactRequest.getId()).orElseThrow();

        contact.setNom(contactRequest.getNom());
        contact.setPrenom(contactRequest.getPrenom());
        contact.setEmail(contactRequest.getEmail());
        contact.setEmailPro(contactRequest.getEmailPro());
        contact.setNumTele1(contactRequest.getNumTele1());
        contact.setNumTele2(contactRequest.getNumTele2());
        contact.setAdresse(contactRequest.getAdresse());
        contact.setGenre(contactRequest.getGenre());

        contactRepositorie.save(contact);
    }


}
