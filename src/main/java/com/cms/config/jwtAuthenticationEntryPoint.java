package com.cms.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
@Component
public class jwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	

	//public jwtAuthenticationEntryPoint(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		// TODO Auto-generated constructor stub
		//String token = (String) session.getAttribute("token");
		//response.setHeader("Authorization", "Bearer "+token);
	//}
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UNAUTHORIZED : SERVER");
	}

}
