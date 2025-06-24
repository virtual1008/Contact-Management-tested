package com.ContactManagement.Contact.Management.Service.controller;

import com.ContactManagement.Contact.Management.Service.dto.request.ContactRequestDTO;
import com.ContactManagement.Contact.Management.Service.dto.response.ContactResponseDTO;
import com.ContactManagement.Contact.Management.Service.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    //Create a new contact
    @PostMapping
    public ResponseEntity<ContactResponseDTO> createContact(@Valid @RequestBody ContactRequestDTO contactRequestDTO) {
        ContactResponseDTO created = contactService.createContact(contactRequestDTO);
        return ResponseEntity.ok(created);
    }

    //Get contact by ID (only if status = 0)
    @GetMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> getContactById(@PathVariable int id) {
        ContactResponseDTO contact = contactService.getContactById(id);
        return ResponseEntity.ok(contact);
    }

    //Get all contacts (only active)
    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> getAllContacts() {
        List<ContactResponseDTO> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }

    // Update contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<ContactResponseDTO> updateContact(
            @PathVariable int id,
            @Valid @RequestBody ContactRequestDTO contactRequestDTO) {
        ContactResponseDTO updated = contactService.updateContact(id, contactRequestDTO);
        return ResponseEntity.ok(updated);
    }

    //Delete (soft delete) contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok("Contact deleted successfully (soft delete)");
    }
}