package com.cms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cms.dao.UserRepository;

import com.cms.entities.User;
@Service
public class UsersService {
	@Autowired  
	UserRepository userRepository;  
	//getting all books record by using the method findaAll() of CrudRepository  
	public List<User> getAllUsers()   
	{  
	List<User> users = new ArrayList<User>();  
	userRepository.findAll().forEach(user1 -> users.add(user1));  
	return users;  
	}  
	//getting a specific record by using the method findById() of CrudRepository  
	public User getUsersById(int id)   
	{  
	return userRepository.findById(id).get();  
	}  
	//saving a specific record by using the method save() of CrudRepository  
	public void saveOrUpdate(User user)   
	{  
     userRepository.save(user);  
	}  
	//deleting a specific record by using the method deleteById() of CrudRepository  
	public void delete(int id)   
	{  
     userRepository.deleteById(id);  
	}  
	//updating a record  
	public void update(User user, int userid)   
	{  
	userRepository.save(user);  
	}  


}
