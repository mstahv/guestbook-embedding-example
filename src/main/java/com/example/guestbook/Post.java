package com.example.guestbook;

import java.time.LocalDateTime;

public class Post {
	private final String name;
	private final String message;
	private final LocalDateTime ts = LocalDateTime.now();
	
	public Post(String name, String message) {
		this.name = name;
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getTs() {
		return ts;
	}

}
