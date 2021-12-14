package com.cms.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;




@Entity
@Table(name="users")
public class User implements UserDetails {

	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 private int id;
	 
	 @NotBlank(message="Name cannot be blank.")
	 @Size(min=2,max=20,message="min 2 and max 20 characters are allowed.")
	 private String name;
	 
	 @Column(unique=true)
	 @Email(message = "Enter a valid email.")
	 private String email;
	 
	 @Column(unique=true)
	 private String username;
	 
	 @Size(min=6,message="Password must contain atleast 6 characters.")
	 private String password;
	 @JsonManagedReference
	 @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
	 private List<Contact> contacts= new ArrayList<>();

	public User() {
		
	}

	

	public User(int id,
			@NotBlank(message = "Name cannot be blank.") @Size(min = 2, max = 20, message = "min 2 and max 20 characters are allowed.") String name,
			@Email(message = "Enter a valid email.") String email, String username,
			@Size(min = 6, message = "Password must contain atleast 6 characters.") String password,
			List<Contact> contacts) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.contacts = contacts;
	}



	

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", username=" + username + ", password="
				+ password + ", contacts=" + contacts + "]";
	}



	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public List<Contact> getContacts() {
		return contacts;
	}



	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<Authority> set= new HashSet<>();
		set.add(new Authority("USER"));
		return set;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
	 
	 
}

