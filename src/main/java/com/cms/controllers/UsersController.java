package com.cms.controllers;

import java.security.Principal;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cms.dao.ContactsRepository;
import com.cms.dao.UserRepository;

import com.cms.entities.Contact;
import com.cms.entities.User;

@Controller
@RequestMapping("/user")
public class UsersController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactsRepository contactRepository;
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName= principal.getName();
		User user = userRepository.findByUsername(userName).get();
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User dashboard");
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	
	@GetMapping("/settings")
	public String settings(Model model) {
		model.addAttribute("title", "Settings");
		return "normal/settings";
	}
	
	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("title", "My Profile");
		return "normal/profile";
	}
	
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, Principal principal, HttpSession session)
	{
		try{
			String name= principal.getName();
		        User user = this.userRepository.findByUsername(name).get();
		        contact.setUser(user);
		        user.getContacts().add(contact);
		        this.userRepository.save(user);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/show-contacts/0";	
	}
	
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page,Model m, Principal principal) {
		m.addAttribute("title", "Show User Contacts");
		String userName = principal.getName();
		User user = this.userRepository.findByUsername(userName).get();
		Pageable pageable= PageRequest.of(page, 5);
		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", contacts.getTotalPages());
		return "normal/show_contacts";
	}
	
	@RequestMapping("/{cId}/contact")
	public String showContactDetail(@PathVariable("cId") Integer cId, Model model, Principal principal) {
		Optional<Contact> contactOptional = this.contactRepository.findById(cId);
		Contact contact = contactOptional.get();
		String userName= principal.getName();
		User user = this.userRepository.findByUsername(userName).get();
		if(user.getId()==contact.getUser().getId()) {
			model.addAttribute("contact", contact);
			model.addAttribute("title",contact.getName());
		}
		return "normal/contact_detail";
	}
	
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cId, Model model, HttpSession session, Principal principal) {
		Contact contact = this.contactRepository.findById(cId).get();
		User user= this.userRepository.findByUsername(principal.getName()).get();
		user.getContacts().remove(contact);
		this.userRepository.save(user);
		return "redirect:/user/show-contacts/0";
	}
	
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid,Model m) {
		m.addAttribute("title", "Update Form");
		Contact contact = this.contactRepository.findById(cid).get();
		m.addAttribute("contact", contact);
		return "normal/update_form";
	}
	
	@RequestMapping(value="/process-update",method=RequestMethod.POST)
	public String updateHandler(@ModelAttribute Contact contact, Model m, HttpSession session, Principal principal) {
		try {
			Contact oldcontactDetail = this.contactRepository.findById(contact.getcId()).get();
			User user= this.userRepository.findByUsername(principal.getName()).get();
			contact.setUser(user);
			this.contactRepository.save(contact);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/"+contact.getcId()+"/contact";
	}
	

}
