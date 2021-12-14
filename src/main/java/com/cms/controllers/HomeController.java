package com.cms.controllers;








import java.net.URI;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse;
import java.util.Optional;

import javax.net.ssl.SSLSession;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.cms.entities.User;

import org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentRendererTokenTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.cms.config.JwtUtil;
import com.cms.dao.UserRepository;
import com.cms.entities.JwtRequest;
import com.cms.entities.JwtResponse;
import com.cms.services.UserDetailsServiceImpl;

import com.cms.entities.User;




@Controller
public class HomeController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
    private JwtUtil jwtUtil;
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	@RequestMapping("/private")
	public void show(HttpRequest request, HttpSession session,HttpResponse response)
	{
		

		
		// add basic authentication header
		
		
		// build the request
		  System.out.println(session.getAttribute("token"));
		
		HttpHeaders headers = request.getHeaders();
		headers.add("Authorization", "Bearer "+session.getAttribute("token"));

		System.out.println(response.headers());
		
		
		
	}
	@RequestMapping("/secret")
	public String secret() {
		return "private";
	}
	

	
	 @RequestMapping("/login") 
	 public String login(Model model) {
		 model.addAttribute("title", "Signin- Smart Contact Manager");
	 
	  return "login";
	  
	 }
	 
	@RequestMapping("/userDetails")
	public String userDetails() {
		return "userDetails";
	}
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title","Register- Smart Contact Manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1, Model model, HttpSession session) {
		try {
			
			
			if(result1.hasErrors()) {
				
				System.out.println("Errors "+ result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			
			
			
			user.setPassword(user.getPassword());
			
			
			System.out.println("User: "+user);
			this.userRepository.save(user);
			model.addAttribute("user", new User());
			
			return "signup";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("user", user);
			
			return "signup";
		}
		
	}
	
	}

	

