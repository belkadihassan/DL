package com.example.DL.controllers;

import com.example.DL.dto.GroupeDTO;
import com.example.DL.dto.GroupeRequest;
import com.example.DL.services.ContactService;
import com.example.DL.services.GroupeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/groupes")
public class GroupeController {
    private final GroupeService groupeService;
    private final ContactService contactService;
    @Autowired
    public GroupeController(GroupeService groupeService, ContactService contactService) {
        this.groupeService = groupeService;
        this.contactService = contactService;
    }
    @GetMapping
    public String showGroupesPage(
            @RequestParam(value = "search" ,defaultValue = "") String search,
            Model model
    ){
        model.addAttribute("groupes", groupeService.getAllGroups(search));
        return "groupes";
    }
    @GetMapping("/{id}")
    public String showGroupeById(
            @PathVariable Long id,
            @RequestParam(value = "search" , defaultValue = "") String search,
            Model model){
        GroupeDTO groupe = groupeService.getGroupById(id);
        model.addAttribute("groupe",groupe);
        model.addAttribute("contactsAvailable", groupeService.getAvailableContacts(id , search));
        model.addAttribute("request", new GroupeRequest(groupe.getNom()));
        return "groupe";
    }
    @PostMapping("/{id}")
    public RedirectView updateGroupeName(
            @PathVariable Long id,
            @RequestParam("name") String nom,
            RedirectAttributes redirectAttributes
    ){
        groupeService.updateGroupeName(id , nom);
        redirectAttributes.addAttribute("success" , "Le groupe a été modifié avec succès");
        return new RedirectView("/groupes/"+id);
    }

    @GetMapping("/create")
    public String showCreateContactPage(Model model){
        model.addAttribute("request" ,new GroupeRequest());
        return "groupeCreate";
    }
    @PostMapping("/create")
    public RedirectView createContact(
            @ModelAttribute("request") @Valid GroupeRequest groupeRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){
        if(bindingResult.hasErrors()){
            redirectAttributes.addAttribute("error" , bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        else {
            groupeService.createGroup(groupeRequest.getNom());
            redirectAttributes.addAttribute("success" , "Le groupe a été enregistré avec succès");
        }
        return new RedirectView("create");
    }
    @PostMapping("/{id}/contacts/add")
    public RedirectView addContact(
            @PathVariable Long id,
            @RequestParam("contacts") List<Long> cid,
            RedirectAttributes redirectAttributes
            ){
        groupeService.addContactToGroupe(id, cid);
        redirectAttributes.addAttribute("success" , "Le contact a été bien ajouté au groupe");
        return new RedirectView("/groupes/" + id);
    }
    @PostMapping("/{id}/contacts/remove")
    public RedirectView removeContacts(
            @PathVariable Long id,
            @RequestParam("contacts") List<Long> cid,
            RedirectAttributes redirectAttributes
    ){
        groupeService.removeContactsFromGroupe(id , cid);
        redirectAttributes.addAttribute("success" , "La list des contacts a été bien supprimé du groupe");
        return new RedirectView("/groupes/"+ id);
    }
    @GetMapping("/{id}/delete")
    public RedirectView deleteGroupe(@PathVariable Long id, RedirectAttributes redirectAttributes){
        groupeService.deleteGroupeById(id);
        redirectAttributes.addAttribute("success" , "Le groupe a été suprrimé avec succès");
        return new RedirectView("/groupes");
    }
}
