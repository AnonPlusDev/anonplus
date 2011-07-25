package com.anonplus;

import java.util.ArrayList;
import java.util.List;

public class MimeType {
	private  String contentType = null;
	private List<String> extensions = new ArrayList<String>();
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public List<String> getExtensions() {
		return extensions;
	}
	public void setExtensions(List<String> extensions) {
		this.extensions = extensions;
	}	 
}
