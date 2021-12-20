
package com.cms.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.cms.entities.Contact;
import com.cms.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Commit
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ContactsRepositoryTest {

	@Autowired
	private ContactsRepository contactsRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	@DirtiesContext
	public void saveContactsTest() {
		Contact contact = new Contact();
		contact.setName("Contact100");
		contact.setEmail("contact100@gmail.com");
		contact.setPhone("9999999999");
		contact.setUser(userRepository.getById(1));
		Contact savedContact = contactsRepository.save(contact);
		Assertions.assertThat(savedContact.getcId()).isGreaterThan(0);

	}

	@Test
	public void getContactTest() {
		Contact contact = contactsRepository.findById(11).get();
		Assertions.assertThat(contact.getcId()).isEqualTo(11);
	}

	@Test
	public void getListOfContactsTest() {
		List<Contact> contacts = (List<Contact>) contactsRepository.findAll();
		Assertions.assertThat(contacts.size()).isGreaterThan(0);

	}

	@Test
	public void updateContactTest() {
		Contact contact = new Contact();
		contact.setName("Contact101");
		contact.setEmail("contact101@gmail.com");
		contact.setPhone("9090909090");
		contact.setUser(userRepository.getById(1));
		contactsRepository.save(contact);
		contact.setEmail("contact101101@gmail.com");
		Contact updatedContact = contactsRepository.save(contact);
		Assertions.assertThat(updatedContact.getEmail()).isEqualTo("contact101101@gmail.com");

	}

	@Test
	public void deleteUserTest() {
		Contact contact = new Contact();
		contact.setName("Contact102");
		contact.setEmail("contact102@gmail.com");
		contact.setPhone("1021021022");
		contact.setUser(userRepository.getById(1));
		Contact savedContact = contactsRepository.save(contact);
		contactsRepository.delete(contact);
		Contact contact1 = null;
		Optional<Contact> optionalContact = contactsRepository.findById(savedContact.getcId());
		if (optionalContact.isPresent()) {
			contact1 = optionalContact.get();
			Assertions.assertThat(contact1).isNull();
		}

	}
}
