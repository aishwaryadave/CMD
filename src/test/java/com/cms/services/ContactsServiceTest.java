package com.cms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cms.dao.ContactsRepository;
import com.cms.dao.UserRepository;
import com.cms.entities.Contact;
import com.cms.entities.User;

@SpringBootTest
class ContactsServiceTest {
	@Autowired
	private ContactsService contactsService;
	@MockBean
	private ContactsRepository contactsRepository;
	@MockBean
	private UserRepository userRepository;

	@Test
	public void getAllContactsTest() {

		when(contactsRepository.findAll()).thenReturn(Stream
				.of(new Contact(2001, "Contact2001", "contact2001@gmail.com", "2001200100",
						new User(1001, "User1001", "user1001@gmail.com", "user1001", "10011001", null)))
				.collect(Collectors.toList()));
		assertEquals(1, contactsService.getAllContacts().size());
	}

	@Test
	public void addContactTest() {
		User user = new User(1002, "User1002", "user1002@gmail.com", "user1002", "10021002", null);
		Contact contact = new Contact(2002, "Contact2002", "contact2002@gmail.com", "2002200200", user);
		when(contactsRepository.save(contact)).thenReturn(contact);
		assertEquals(contact, contactsService.save(contact));
	}

	@Test
	public void deleteContactTest() {
		User user = new User(1003, "User1003", "user1003@gmail.com", "user1003", "10031003", null);
		Contact contact = new Contact(2003, "Contact2003", "contact2003@gmail.com", "2003200333", user);
		contactsService.delete(contact.getcId());
		verify(contactsRepository, times(1)).deleteById(contact.getcId());
	}

	@Test
	public void updateContactTest() {
		User user = new User(1004, "User1004", "user1004@gmail.com", "user1004", "10041004", null);
		Contact contact = new Contact(2004, "Contact2004", "contact2004@gmail.com", "2004200444", user);

		when(contactsRepository.save(contact)).thenReturn(contact);
		contact.setEmail("contact2400@gmail.com");
		contactsService.update(contact, 2004);
		verify(contactsRepository, times(1)).save(contact);
	}

}
