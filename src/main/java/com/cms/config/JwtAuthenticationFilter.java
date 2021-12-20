package com.cms.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpMessage;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cms.controllers.AuthenticateController;
import com.cms.services.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticateController authenticateController;
	
	private static String URI = "http://localhost:8080/";
	
	
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		CloseableHttpClient client = HttpClients.custom().build();
		HttpUriRequest request1 = RequestBuilder.post().setUri(URI)
		.setHeader(HttpHeaders.AUTHORIZATION,"Bearer "+authenticateController.token).build();
		
		final String requestTokenHeader=request1.getAllHeaders()[0].getValue();
		//final String requestTokenHeader= request.getHeader("Authorization");
		System.out.println(requestTokenHeader);
		String username = null;
		String jwtToken = null;
		
		if(requestTokenHeader!= null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			
			try {
				username = this.jwtUtil.extractUsername(jwtToken);
			} catch (ExpiredJwtException e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Jwt Token Expired!");
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("error");
			}
			
			
		}else {
			System.out.println("Invalid Token");
		}
		if(username != null && SecurityContextHolder.getContext().getAuthentication()== null)
		{
			final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtUtil.validateToken(jwtToken, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}else {
			System.out.println("Token is not valid");
		}
		//UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		//String token = this.jwtUtil.generateToken(userDetails);
		//response.addHeader("Authorization", "Bearer "+token);
		
		filterChain.doFilter(request, response);
		
	}

}
