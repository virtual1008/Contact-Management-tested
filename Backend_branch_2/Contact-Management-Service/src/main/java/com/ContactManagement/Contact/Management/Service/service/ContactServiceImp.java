package com.ContactManagement.Contact.Management.Service.service;

import com.ContactManagement.Contact.Management.Service.dto.request.ContactRequestDTO;
import com.ContactManagement.Contact.Management.Service.dto.response.ContactResponseDTO;
import com.ContactManagement.Contact.Management.Service.model.Contact;
import com.ContactManagement.Contact.Management.Service.model.Gender;
import com.ContactManagement.Contact.Management.Service.repository.ContactRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImp implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        // Skip id and status when mapping from DTO to entity
        modelMapper.typeMap(ContactRequestDTO.class, Contact.class).addMappings(mapper -> {
            mapper.skip(Contact::setId);
            mapper.skip(Contact::setStatus);

            // Map gender String to Gender enum if needed
            mapper.<Gender>map(src -> {
                if (src.getGender() == null) return null;
                return Gender.valueOf(src.getGender().toUpperCase());
            }, Contact::setGender);
        });
    }

    public ContactResponseDTO createContact(ContactRequestDTO contactRequestDTO) {
        if (contactRepository.existsByPhoneNumber(contactRequestDTO.getPhoneNumber()) ||
                contactRepository.existsByEmailId(contactRequestDTO.getEmailId())) {
            throw new RuntimeException("Phone number or email already exists.");
        }

        Contact contact = modelMapper.map(contactRequestDTO, Contact.class);
        Contact saved = contactRepository.save(contact);
        return modelMapper.map(saved, ContactResponseDTO.class);
    }


    @Override
    public ContactResponseDTO getContactById(int id) {
        Contact contact = contactRepository.findByIdAndStatus(id, 0)
                .orElseThrow(() -> new RuntimeException("Active contact not found"));
        return modelMapper.map(contact, ContactResponseDTO.class);
    }

    @Override
    public List<ContactResponseDTO> getAllContacts() {
        return contactRepository.findAllByStatus(0).stream()
                .map(contact -> modelMapper.map(contact, ContactResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContactResponseDTO updateContact(int id, ContactRequestDTO contactRequestDTO) {
        Contact existing = contactRepository.findByIdAndStatus(id, 0)
                .orElseThrow(() -> new RuntimeException("Active contact not found"));

        modelMapper.map(contactRequestDTO, existing);  // Apply updates
        Contact updated = contactRepository.save(existing);
        return modelMapper.map(updated, ContactResponseDTO.class);
    }

    @Override
    public void deleteContact(int id) {
        Contact contact = contactRepository.findByIdAndStatus(id, 0)
                .orElseThrow(() -> new RuntimeException("Active contact not found"));

        contact.setStatus(1);  // Soft delete
        contactRepository.save(contact);
    }
}
