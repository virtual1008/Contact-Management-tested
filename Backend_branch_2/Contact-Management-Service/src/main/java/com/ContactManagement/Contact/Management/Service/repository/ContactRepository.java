package com.ContactManagement.Contact.Management.Service.repository;

import com.ContactManagement.Contact.Management.Service.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailId(String emailId);

    Optional<Contact> findByIdAndStatus(int id, int status);

    List<Contact> findAllByStatus(int status);
}
