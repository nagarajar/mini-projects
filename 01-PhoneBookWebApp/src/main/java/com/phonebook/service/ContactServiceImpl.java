package com.phonebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonebook.entity.Contact;
import com.phonebook.repository.ContactRepository;

@Service
public class ContactServiceImpl implements IContactService {
	
	@Autowired
	private ContactRepository repo;

	@Override
	public String saveContact(Contact contact) {
		contact.setActiveSw("Y");
		contact = repo.save(contact);
		if(contact.getContactId() != null) {			
			return "Contact Saved Successfully";
		}else {
			return "Contact Failed To Save";			
		}
	}

	@Override
	public List<Contact> getAllContacts() {	
		//return repo.findAll();
		//For Soft delete
		return repo.findByActiveSw("Y");
	}

	@Override
	public Contact getContactById(Integer id) {
		Optional<Contact> findById = repo.findById(id);
		if(findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public String updateContact(Contact contact) {
		if(repo.existsById(contact.getContactId())) {
			repo.save(contact);
			return "Contact Updated Successfully";
		}else {			
			return "Contact Not Found";
		}
	}

	@Override
	public String deleteContactById(Integer id) {
		/*
		 * if(repo.existsById(id)) { repo.deleteById(id); return
		 * "Contact Deleted Successfully"; }else { return "Contact Not Found"; }
		 */
		
		// Implementing Soft Delete Process
		// Hard Delete: Deleting the record permanently from DB
		// Soft Delete: Instead of Deleting the record permanently from DB we can In-Activate the
		//  record if we don't want, so in future you can retrieve from DB.
		Contact contactById = getContactById(id);
		if(contactById != null) {			
			contactById.setActiveSw("N");
			repo.save(contactById);
			return "Contact Deleted Successfully";
		}else {			
			return "Contact Not Found";
		}
	}

}
