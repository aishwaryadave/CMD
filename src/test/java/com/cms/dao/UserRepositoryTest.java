
package com.cms.dao;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.cms.entities.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)

class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	@Order(1)
	public void saveUserTest() {
		User expectedUser = new User();
		expectedUser.setName("User21");
		expectedUser.setUsername("user21");
		expectedUser.setEmail("user21@gmail.com");
		expectedUser.setPassword("212121");
		expectedUser.setContacts(null);
		userRepository.save(expectedUser);
		Assertions.assertThat(expectedUser.getId()).isGreaterThan(0);
	}

	@Test
	@Order(2)
	public void getUserTest() {
		User user = userRepository.findById(1).get();
		Assertions.assertThat(user.getId()).isEqualTo(1);
	}

	@Test
	@Order(3)
	public void getListOfUsersTest() {
		List<User> users = userRepository.findAll();
		Assertions.assertThat(users.size()).isGreaterThan(0);
	}

	@Test
	@Order(4)
	public void updateUserTest() {
		User expectedUser = new User();
		expectedUser.setName("User22");
		expectedUser.setUsername("user22");
		expectedUser.setEmail("user22@gmail.com");
		expectedUser.setPassword("222222");
		expectedUser.setContacts(null);
		userRepository.save(expectedUser);
                expectedUser.setEmail("user2222@gmail.com");
		User userUpdated = userRepository.save(expectedUser);
		Assertions.assertThat(expectedUser.getEmail()).isEqualTo("user2222@gmail.com");

	}

	@Test
	@Order(5)
	public void deleteUserTest() {
                User expectedUser = new User();
                expectedUser.setName("User23");
                expectedUser.setUsername("user23");
		expectedUser.setEmail("user23@gmail.com");
		expectedUser.setPassword("232323");
		expectedUser.setContacts(null);
		User user = userRepository.save(expectedUser);
		userRepository.delete(expectedUser);
		User user1 = null;
		Optional<User> optionalUser = userRepository.findByUsername("user23");
		if (optionalUser.isPresent()) {
			user1 = optionalUser.get();
			Assertions.assertThat(user1).isNull();
		}

	}

}
