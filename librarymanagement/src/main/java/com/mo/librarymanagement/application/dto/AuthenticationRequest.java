package com.mo.librarymanagement.application.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {
	@NotBlank(message = "Username is required.")
    private String username;
	@NotBlank(message = "Password is required.")
    private String password;
	
	public String getUsername() {
		return username;
	}
	public AuthenticationRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    // Getters and setters
}