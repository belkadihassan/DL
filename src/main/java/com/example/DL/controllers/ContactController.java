package com.example.DL.controllers;

import com.example.DL.dto.ContactDTO;
import com.example.DL.dto.ContactRequest;
import com.example.DL.services.ContactService;
import jakarta.validation.Valid;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static com.example.DL.dto.ContactMapper.mapToContact;

@Controller
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String showContacts(
            @RequestParam(value = "search" , defaultValue = "") String name,
            @RequestParam(value = "searchByTele",defaultValue = "") String tele,
            @RequestParam(value = "typeSearch" , defaultValue = "tele1") String type,
            Model model
    ){
        List<ContactDTO> contacts = contactService.getAllContacts(name , tele , type);
        model.addAttribute("contacts",contacts);
        return "contacts";
    }
    @GetMapping("/{id}")
    public String showContactById(@PathVariable Long id , Model model){
        ContactDTO contact = contactService.getContactById(id);
        model.addAttribute("contact",contact);
        model.addAttribute("request", new ContactRequest());
        return "contact";
    }
    @PostMapping("/{id}")
    public RedirectView updateContact(
            @PathVariable Long id,
            @ModelAttribute("request") @Valid ContactRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
            ){
        if(bindingResult.hasErrors()){
            redirectAttributes.addAttribute("error" , bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        else {
            contactService.updateContact(request);
            redirectAttributes.addAttribute("success" ,"Le contact a été modifié avec succès");
        }

        return new RedirectView("/contacts/" + id);
    }
    @GetMapping("/create")
    public String showCreateContactPage(Model model){
        model.addAttribute("request" ,new ContactRequest());
        return "contactCreate";
    }
    @PostMapping("/create")
    public RedirectView createContact(
            @ModelAttribute("request") @Valid ContactRequest contactRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){
        if(bindingResult.hasErrors()){
            redirectAttributes.addAttribute("error" , bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        else {
            contactService.addContact(mapToContact(contactRequest));
            redirectAttributes.addAttribute("success" , "Le contact a été enregistré avec succès");
        }
        return new RedirectView("create");
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteContactById(
            @PathVariable Long id,
            RedirectAttributes redAtt
    ){
        contactService.deleteContactById(id);
        redAtt.addAttribute("success" , "le contact a été bien supprimé");
        System.out.println(id);
        return new RedirectView("/contacts");
    }

}
