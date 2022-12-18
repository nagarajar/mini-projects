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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/app/contact")
@CrossOrigin("http://localhost:4200")
@Api(value = "This is Contact RestController")
public class ContactRestController 
{
	@Autowired
	private IContactService service;
	
	@ApiOperation(value = "This is used to create new contact")
	@PostMapping("/create")
	public ResponseEntity<String> createContact(@RequestBody Contact contact){
		String msg = service.saveContact(contact);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "This is used to get all contacts")
	@GetMapping("/all")
	ResponseEntity<List<Contact>> getAllContacts(){
		List<Contact> contacts = service.getAllContacts();
		return new ResponseEntity<List<Contact>>(contacts,HttpStatus.OK);
	}
	
	@ApiOperation(value = "This is used to get one contact by id")
	@GetMapping("/find/{id}")
	ResponseEntity<?> getOneContact(@PathVariable Integer id){
		Contact contact = service.getContactById(id);
		if(contact != null) {			
			return new ResponseEntity<Contact>(contact, HttpStatus.OK);
		}
		String msg = "Contact Not Found";
		return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
	}
	
	@ApiOperation(value = "This is used to update contact")
	@PutMapping("/modify")
	ResponseEntity<String> modifyContact(@RequestBody Contact contact){
		String msg = service.updateContact(contact);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@ApiOperation(value = "This is used to delete contact by id")
	@DeleteMapping("/remove/{id}")
	ResponseEntity<String> deleteContact(@PathVariable Integer id){
		String msg = service.deleteContactById(id);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
}
