package com.cms.controllers;

import java.net.URI;





import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.cms.entities.User;


import org.springframework.beans.factory.annotation.Autowired;






import org.springframework.security.authentication.AuthenticationManager;





import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




import com.cms.config.JwtUtil;
import com.cms.dao.UserRepository;


import com.cms.services.UserDetailsServiceImpl;



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
	@Autowired
	private AuthenticateController authenticateController;

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping("/about")
	public String about() {
		return "about";
	}

	@RequestMapping("/private")
	public String show(@CookieValue("token") String token,
			HttpServletResponse response/* @RequestHeader(name = "Authorization",value = "Bearer "+token) */) {

		System.out.println(token);
		// HttpHeaders headers = new HttpHeaders();
		// headers.set("Authorization", "Bearer " + token);
		response.addHeader("Authorization", "Bearer " + token);

		// add basic authentication header

		// build the request
		// System.out.println(session.getAttribute("token"));

		// HttpHeaders headers = request.getHeaders();
		// headers.add("Authorization", "Bearer "+token);

		// System.out.println(response.headers());
		return "private";

	}
	// @RequestMapping("/secret")
	// public String secret() {
	// return "private";
	// }

	@RequestMapping("/login")
	public String login(Model model) {
		authenticateController.token="";
		
		model.addAttribute("title", "Signin- Smart Contact Manager");

		return "login";

	}

	@RequestMapping("/userDetails")
	public String userDetails() {
		return "userDetails";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register- Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1, Model model,
			HttpSession session) {
		try {

			if (result1.hasErrors()) {

				System.out.println("Errors " + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setUsername(user.getUsername());
			user.setPassword(user.getPassword());

			System.out.println("User: " + user);
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
