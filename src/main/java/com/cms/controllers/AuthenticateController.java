package com.cms.controllers;

import java.security.Principal;
 
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cms.config.JwtUtil;

import com.cms.entities.User;

import com.cms.services.UserDetailsServiceImpl;

@Controller
public class AuthenticateController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	public String token;

        @PostMapping("/generate-token")
	public String generateToken(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model, Principal principal, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		try {

			authenticate(username, password);

		} catch (UsernameNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("User not found!");
		}
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		token = this.jwtUtil.generateToken(userDetails);
		session.setAttribute("token", token);
		Cookie cookie = new Cookie("token",token);
	        response.addCookie(cookie);

		return "redirect:/user/index";

	}

	private void authenticate(String username, String password) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER DISABLED " + e.getMessage());
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID CREDENTIALS " + e.getMessage());
		}
	}

	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		return ((User) this.userDetailsService.loadUserByUsername(principal.getName()));

	}
}
