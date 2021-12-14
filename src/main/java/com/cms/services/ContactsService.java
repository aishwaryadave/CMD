package com.cms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dao.ContactsRepository;
import com.cms.entities.Contact;

@Service
public class ContactsService {
	@Autowired  
	ContactsRepository contactsRepository;  
	//getting all books record by using the method findaAll() of CrudRepository  
	public List<Contact> getAllContacts()   
	{  
	List<Contact> contacts = new ArrayList<Contact>();  
	contactsRepository.findAll().forEach(contacts1 -> contacts.add(contacts1));  
	return contacts;  
	}  
	//getting a specific record by using the method findById() of CrudRepository  
	public Contact getContactsById(int id)   
	{  
	return contactsRepository.findById(id).get();  
	}  
	//saving a specific record by using the method save() of CrudRepository  
	public void saveOrUpdate(Contact contact)   
	{  
     contactsRepository.save(contact);  
	}  
	//deleting a specific record by using the method deleteById() of CrudRepository  
	public void delete(int id)   
	{  
     contactsRepository.deleteById(id);  
	}  
	//updating a record  
	public void update(Contact contact, int contactid)   
	{  
	contactsRepository.save(contact);  
	}  

}
