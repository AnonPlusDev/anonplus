package com.anonplus;

/**
 *  Constants file to hold commonly used String constants.
 * @author anonplus dev team.
 */

public interface Constants {
	
	String HTTP_VERSION = "HTTP/1.0 ";
	
	String HTTP_OK = "200 OK";
	
	String HTTP_BAD_REQ = "400 Bad Request";
	
	String HTTP_FORBIDDEN = "403 Forbidden";
	
	String HTTP_URL_NOT_FOUND = "404 Not Found";
	
	String HTTP_INTERNAL_SERVER_ERROR = "500 Internal Server Error";
	
	String HTTP_NOT_IMPLEMENTED = "501 Not Implemented";
	
	String NEWLINE_SEPARATOR = System.getProperty("line.separator");
	
	String HTML_CONTENT_TYPE = "text/html";
	
	String HTTP_GET_METHOD = "GET";
	
	String HTTP_HEAD_METHOD = "HEAD";
	
}

