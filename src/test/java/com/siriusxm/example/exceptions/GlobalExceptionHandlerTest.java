package com.siriusxm.example.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    void testHandleMethodNotSupported() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("GET");
        Mockito.when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("https://test.url-get.com"));

        ErrorInfo errorInfo = handler.handleMethodNotSupported(mockRequest, mockResponse, ex);

        assertNotNull(errorInfo);
        assertTrue(errorInfo.getMessage().contains("Request method 'GET' is not supported"));
        Mockito.verify(mockRequest).getRequestURL();
    }

    @Test
    void testHandleMultipartException() {
        MultipartException ex = new MultipartException("Multipart error");
        Mockito.when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("https://test.url.com"));

        ErrorInfo errorInfo = handler.handleMultipartException(mockRequest, mockResponse, ex);

        assertNotNull(errorInfo);
        assertEquals("Invalid File Upload Request", errorInfo.getMessage());
        Mockito.verify(mockRequest).getRequestURL();
    }

    @Test
    void testHandleIOException() {
        IOException ex = new IOException("IO error occurred");
        Mockito.when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("https://test.url-io.com"));

        ErrorInfo errorInfo = handler.handleIOException(mockRequest, mockResponse, ex);

        assertNotNull(errorInfo);
        assertEquals("IO error occurred", errorInfo.getMessage());
        Mockito.verify(mockRequest).getRequestURL();
    }

    @Test
    void testHandleURISyntaxException() {
        URISyntaxException ex = new URISyntaxException("uri", "bad syntax");
        Mockito.when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("https://test.uri.com"));

        ErrorInfo errorInfo = handler.handleURISyntaxException(mockRequest, mockResponse, ex);

        assertNotNull(errorInfo);
        assertTrue(errorInfo.getMessage().contains("bad syntax"));
        Mockito.verify(mockRequest).getRequestURL();
    }

    @Test
    void testHandleException() {
        Exception ex = new Exception("General exception");
        ErrorInfo errorInfo = handler.handleException(mockRequest, mockResponse, ex);
        assertNotNull(errorInfo);
        assertEquals("General exception", errorInfo.getMessage());
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Runtime error");
        ErrorInfo errorInfo = handler.handleRuntimeException(mockRequest, mockResponse, ex);
        assertNotNull(errorInfo);
        assertEquals("Runtime error", errorInfo.getMessage());
    }

    @Test
    void testHandleDataIntegrityViolationException() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Integrity violation");
        ErrorInfo errorInfo = handler.handleDataIntegrityViolationException(mockRequest, mockResponse, ex);
        assertNotNull(errorInfo);
        assertEquals("Integrity violation", errorInfo.getMessage());
    }

    @Test
    void testHandleConstraintViolationException() {
        // Using a real ConstraintViolationException requires a SQL exception, so we'll simulate with a mock
        ConstraintViolationException ex = Mockito.mock(ConstraintViolationException.class);
        Mockito.when(ex.getMessage()).thenReturn("Constraint violation");
        ErrorInfo errorInfo = handler.handleConstraintViolationException(mockRequest, mockResponse, ex);
        assertNotNull(errorInfo);
        assertEquals("Constraint violation", errorInfo.getMessage());
    }

    @Test
    void testHandleInvalidParameterException() {
        InvalidParameterException ex = new InvalidParameterException("Invalid parameter");
        ErrorInfo errorInfo = handler.handleInvalidParameterException(mockRequest, mockResponse, ex);
        assertNotNull(errorInfo);
        assertEquals("Invalid parameter", errorInfo.getMessage());
    }
}
