package com.phonebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.phonebook.entity.Contact;
import com.phonebook.repository.ContactRepository;

public class ContactServiceImpl implements IContactService {
	
	@Autowired
	private ContactRepository repo;

	@Override
	public String saveContact(Contact contact) {
		repo.save(contact);
		return "Contact Saved Successfully";
	}

	@Override
	public List<Contact> getAllContacts() {	
		return repo.findAll();
	}

	@Override
	public Contact getContactById(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public String updateContact(Contact contact) {
		repo.save(contact);
		return "Contact Updated Successfully";
	}

	@Override
	public String deleteContactById(Integer id) {
		repo.deleteById(id);
		return "Contact Deleted Successfully";
	}

}
