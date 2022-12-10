package com.phonebook.service;

import java.util.List;

import com.phonebook.entity.Contact;

public interface IContactService 
{
	String saveContact(Contact contact);
	List<Contact> getAllContacts();
	Contact getContactById(Integer id);
	String updateContact(Contact contact);
	String deleteContactById(Integer id);
	
}
