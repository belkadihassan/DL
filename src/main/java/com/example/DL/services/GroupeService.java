package com.example.DL.services;

import com.example.DL.Exceptions.ContactNotFoundException;
import com.example.DL.dto.ContactDTO;
import com.example.DL.dto.ContactMapper;
import com.example.DL.dto.GroupeDTO;
import com.example.DL.dto.GroupeMapper;
import com.example.DL.models.Contact;
import com.example.DL.models.Groupe;
import com.example.DL.repositories.ContactRepository;
import com.example.DL.repositories.GroupeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupeService {

    private final ContactRepository contactRepository;
    private final GroupeRepository groupeRepository;

    @Autowired
    public GroupeService(ContactRepository contactRepository , GroupeRepository groupeRepository) {
        this.contactRepository = contactRepository;
        this.groupeRepository = groupeRepository;
    }
    public List<GroupeDTO> getAllGroups(String search) {
        if(search.isEmpty()){
            return GroupeMapper.mapAllToGroupeDTO(groupeRepository.findAll(Sort.by(Sort.Order.asc("nom"))));
        }
        else return GroupeMapper.mapAllToGroupeDTO(groupeRepository.searchBynom(search, Sort.by(Sort.Order.asc("nom"))));
    }

    public GroupeDTO getGroupById(Long groupId) {
        return GroupeMapper.mapToGroupeDTO(findGroupeById(groupId));
    }

    public void createGroup(String groupName) {
        Groupe group = Groupe.builder()
                .nom(groupName)
                .build();
        groupeRepository.save(group);
    }


    public void createGroupByContactFullname(Contact contact) {
        String fullname = contact.getPrenom() + " " + contact.getNom();
        Optional<Groupe> groupOptional = groupeRepository.findByNom(fullname.toLowerCase());

        if (groupOptional.isEmpty()) {
            Groupe group = Groupe.builder()
                    .nom(fullname)
                    .contacts(List.of(contact))
                    .build();
            groupeRepository.save(group);
        } else {
            groupOptional.get().getContacts().add(contact);
            groupeRepository.save(groupOptional.get());
        }
    }

    public void updateGroupeName(Long groupId, String name) {
        Groupe groupe = findGroupeById(groupId);
        groupe.setNom(name);
        groupeRepository.save(groupe);
    }

    public void addContactToGroupe(Long groupId, List<Long> contactIds) throws ContactNotFoundException {
        Groupe group = findGroupeById(groupId);
        List<Contact> contacts = contactRepository.findAllById(contactIds);
        group.getContacts().addAll(contacts);
    }
    public void removeContactsFromGroupe(Long id, List<Long> cid) {
        Groupe groupe = findGroupeById(id);
        List<Contact> contacts = contactRepository.findAllById(cid);
        groupe.getContacts().removeAll(contacts);
    }
    public void deleteGroupeById(Long groupId) {
        Groupe groupe = findGroupeById(groupId);
        groupe.getContacts().forEach(c -> c.getGroupes().remove(groupe));
        contactRepository.saveAll(groupe.getContacts());
        groupeRepository.delete(groupe);
    }

    public Groupe findGroupeById(Long groupId) {
        return groupeRepository.findById(groupId)
                .orElseThrow();
    }
    public List<ContactDTO> getAvailableContacts(Long groupeId , String search){
        List<Contact> contacts = contactRepository.searchByName(search , Sort.by(Sort.Order.asc("nom")));
        contacts.removeAll(findGroupeById(groupeId).getContacts());
        return ContactMapper.mapAllToContactDTO(contacts);
    }
}