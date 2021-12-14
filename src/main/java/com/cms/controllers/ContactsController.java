package com.cms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cms.entities.Contact;
import com.cms.services.ContactsService;
@RestController
public class ContactsController {
	
	
	//autowire the BooksService class  
	@Autowired  
	ContactsService contactsService;  
	//creating a get mapping that retrieves all the books detail from the database   
	@GetMapping("/contacts")  
	private List<Contact> getAllContacts()   
	{  
	return contactsService.getAllContacts();  
	}  
	//creating a get mapping that retrieves the detail of a specific book  
	@GetMapping("/contacts/{contactid}")  
	private Contact getBooks(@PathVariable("contactid") int contactid)   
	{  
	return contactsService.getContactsById(contactid);  
	}  
	//creating a delete mapping that deletes a specified book  
	@DeleteMapping("/contacts/{contactid}")  
	private void deleteBook(@PathVariable("contactid") int contactid)   
	{  
	contactsService.delete(contactid);  
	}  
	//creating post mapping that post the book detail in the database  
	@PostMapping("/contacts")  
	private int saveBook(@RequestBody Contact contact)   
	{  
	contactsService.saveOrUpdate(contact);  
	return contact.getcId();  
	}  
	//creating put mapping that updates the book detail   
	@PutMapping("/contacts")  
	private Contact update(@RequestBody Contact contact)   
	{  
	contactsService.saveOrUpdate(contact);  
	return contact;  
	}  
	}  


