package com.siriusxm.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ResponseBody
	public ErrorInfo handleMethodNotSupported(HttpServletRequest request,
											  HttpServletResponse response, Exception ex) {
		log.error("Handling method not supported exception");
		return new ErrorInfo(request, ex);
	}
	
	@ExceptionHandler(MultipartException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorInfo handleMultipartException(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		log.error("Handling multipart exception");
		return new ErrorInfo(request, "Invalid File Upload Request");
	}	
	
	@ExceptionHandler(IOException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorInfo handleIOException(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		log.error("Handling IOException");
		return new ErrorInfo(request, ex.getMessage());
	}

	@ExceptionHandler(URISyntaxException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorInfo handleURISyntaxException(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		log.error("Handling URISyntaxException");
		return new ErrorInfo(request, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorInfo handleException(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		log.error("Handling Exception");
        return new ErrorInfo(request.getRequestURI(), ex.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorInfo handleRuntimeException(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		log.error("Handling Runtime Exception");
        return new ErrorInfo(request.getRequestURI(), ex.getMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorInfo handleDataIntegrityViolationException(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		log.error("Handling DataIntegrityViolationException Exception");
        return new ErrorInfo(request.getRequestURI(), ex.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo handleConstraintViolationException(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
		log.error("Handling ConstraintViolationException");
        return new ErrorInfo(request.getRequestURI(), ex.getMessage());
	}

	@ExceptionHandler(InvalidParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorInfo handleInvalidParameterException(HttpServletRequest request,
									 HttpServletResponse response, Exception ex) {
		log.error("Handling InvalidParameterException");
        return new ErrorInfo(request.getRequestURI(), ex.getMessage());
	}
}
