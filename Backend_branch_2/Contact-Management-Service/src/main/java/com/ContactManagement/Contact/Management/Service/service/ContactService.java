package com.ContactManagement.Contact.Management.Service.service;

import com.ContactManagement.Contact.Management.Service.dto.request.ContactRequestDTO;
import com.ContactManagement.Contact.Management.Service.dto.response.ContactResponseDTO;

import java.util.List;

public interface ContactService {

    // Create Contact
    ContactResponseDTO createContact(ContactRequestDTO contactRequestDTO);

    // Get Contact by ID
    ContactResponseDTO getContactById(int id);

    // Get All Contacts (only status = 0)
    List<ContactResponseDTO> getAllContacts();

    // Update Contact by ID
    ContactResponseDTO updateContact(int id, ContactRequestDTO contactRequestDTO);

    // Soft Delete Contact
    void deleteContact(int id);
}
