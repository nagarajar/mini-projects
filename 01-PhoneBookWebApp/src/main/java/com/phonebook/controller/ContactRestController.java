package com.phonebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonebook.entity.Contact;
import com.phonebook.service.IContactService;

@RestController
@RequestMapping("/v1/app/contact")
@CrossOrigin
public class ContactRestController 
{
	@Autowired
	private IContactService service;
	
	@PostMapping("/create")
	public ResponseEntity<String> createContact(@RequestBody Contact contact){
		String msg = service.saveContact(contact);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	ResponseEntity<List<Contact>> getAllContacts(){
		List<Contact> contacts = service.getAllContacts();
		return new ResponseEntity<List<Contact>>(contacts,HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	ResponseEntity<?> getOneContact(@PathVariable Integer id){
		Contact contact = service.getContactById(id);
		if(contact != null) {			
			return new ResponseEntity<Contact>(contact, HttpStatus.OK);
		}
		String msg = "Contact Not Found";
		return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/modify")
	ResponseEntity<String> modifyContact(@RequestBody Contact contact){
		String msg = service.updateContact(contact);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@DeleteMapping("/remove/{id}")
	ResponseEntity<String> deleteContact(@PathVariable Integer id){
		String msg = service.deleteContactById(id);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
}
