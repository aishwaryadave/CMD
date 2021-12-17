package com.cms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cms.dao.UserRepository;
import com.cms.entities.User;

@SpringBootTest
public class UsersServiceTest {
	@Autowired
	private UsersService usersService;
	@MockBean
	private UserRepository userRepository;

	@Test
	public void getAllUsersTest() {

		when(userRepository.findAll())
				.thenReturn(Stream.of(new User(1001, "User1001", "user1001@gmail.com", "user1001", "10011001", null))
						.collect(Collectors.toList()));
		assertEquals(1, usersService.getAllUsers().size());
	}

	@Test
	public void addUserTest() {
		User user = new User(1002, "User1002", "user1002@gmail.com", "user1002", "10021002", null);
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, usersService.save(user));
	}

	@Test
	public void deleteUserTest() {
		User user = new User(1003, "User1003", "user1003@gmail.com", "user1003", "10031003", null);
		usersService.delete(user.getId());
		verify(userRepository, times(1)).deleteById(user.getId());
	}

	@Test
	public void updateUserTest() {
		User user = new User(1002, "User1004", "user1004@gmail.com", "user1004", "10041004", null);
		when(userRepository.save(user)).thenReturn(user);
		user.setEmail("user1200@gmail.com");
		usersService.update(user, 1004);
		verify(userRepository, times(1)).save(user);
	}

}
