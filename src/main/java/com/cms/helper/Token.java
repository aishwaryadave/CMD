package com.cms.helper;

public class Token {
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Token(String token) {
		super();
		this.token = token;
	}
	public Token() {
		
		
	}

	@Override
	public String toString() {
		return "Token [token=" + token + "]";
	}


}
