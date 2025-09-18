package com.siriusxm.example.exceptions;

import javax.servlet.http.HttpServletRequest;

public class ErrorInfo {

	private final String url;

	private final String message;

	public ErrorInfo(String url, String message) {
		this.url = url;
		this.message = message;
	}
	
	public ErrorInfo(HttpServletRequest request, Exception ex) {
		this.url = request.getRequestURL().toString();
		this.message = ex.getMessage();
	}
	
	public ErrorInfo(HttpServletRequest request, String message) {
		this.url = request.getRequestURL().toString();
		this.message = message;
	}		

	public String getUrl() {
		return url;
	}

	public String getMessage() {
		return message;
	}
}
