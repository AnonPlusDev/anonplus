package com.anonplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SimpleWebServerConfig {
	
	private Integer port;
	private String config_filename;
	private String documentRoot;
	private List<String> DirectoryIndex;
	private String error400page;
	private String error403page;
	private String error404page;
	private String error500page;
	private String error501page;
	private String mimeFile;
	private MimeTypeList mimeTypes;
	
	public void readConfigurationFile(String filename) throws IOException {
		InputStream configFileStream = SimpleWebServerConfig.class.getResourceAsStream("/resources/" + filename);
		if(configFileStream == null) {
			throw new IOException("Path: " + filename + " does not exist, is a directory or it's not readable!\n");
		}
		Global.message("Reading " + filename + " !\n");
		 
		Properties p = new Properties();
		p.load(configFileStream);
		
		port = Integer.parseInt(p.getProperty("Port", "8080"));
		documentRoot = p.getProperty("DocumentRoot", "./public_html");
		documentRoot = new File(documentRoot).getAbsolutePath();		
		Global.message(" ++++++++ " + documentRoot );
		String tmpList[] = p.getProperty("DirectoryIndex", "index.html default.html").split(" ");
		this.DirectoryIndex = new ArrayList<String>();
		for(int i=0; i < tmpList.length; ++i) {
			this.DirectoryIndex.add(tmpList[i]);			
			if(Global.DEBUG) Global.message("Adding to DirectoryIndex: " + tmpList[i] );		
			
		}
		mimeFile = p.getProperty("MimeFile", "mime.types");
		//mimeFile = new File(mimeFile).getAbsolutePath();	
		Global.message(" ++++++++ " + mimeFile );
		
		documentRoot = p.getProperty("DocumentRoot", "public_html");
		if(Global.DEBUG) p.list(System.out);		
		readMimeFile();
		
	}
	
	private void readMimeFile() throws IOException {
	
		InputStream configFileStream = SimpleWebServerConfig.class.getResourceAsStream("/resources/" + mimeFile);
		if(configFileStream == null)
		{
			throw new IOException("Path: " + mimeFile + " does not exist, is a directory or it's not readble!\n");
		} 
		
		InputStreamReader reader = new InputStreamReader(configFileStream);
		BufferedReader br = new BufferedReader(reader);
		String bufferLine;
		Pattern p = Pattern.compile("#.*$");
		Global.message("-------------------------------------");
		Global.message("-------------------------------------");
		Global.message("-------------------------------------");
		Global.message("           REGEX                     ");
		
		mimeTypes = new MimeTypeList(); 
		do 
		{
			bufferLine = br.readLine();
			if(bufferLine == null) continue; 
			
			Matcher m = p.matcher(bufferLine);
			String result = m.replaceAll("");			
			Global.message(result);
			String sList[] = result.split("\\s+");
			
			if(sList.length < 2) continue;		// Continue in case of empty or not having the extension
			
			MimeType mimeType = new MimeType();
			for(int i = 0; i < sList.length; ++i)
			{
				if(i < 1)
					mimeType.setContentType(sList[i]);
				else
					mimeType.getExtensions().add(sList[i]);				
			}
			mimeTypes.list.add(mimeType);
			Global.message("Adding Mime: contentType ->  " + mimeType.getContentType());
			Global.message("Adding Mime: extensions ->  " + mimeType.getExtensions().toString() );
				
		} while(bufferLine != null);		
	}
	
	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the config_filename
	 */
	public String getConfig_filename() {
		return config_filename;
	}

	/**
	 * @param config_filename the config_filename to set
	 */
	public void setConfig_filename(String config_filename) {
		this.config_filename = config_filename;
	}

	/**
	 * @return the documentRoot
	 */
	public String getDocumentRoot() {
		return documentRoot;
	}

	/**
	 * @param documentRoot the documentRoot to set
	 */
	public void setDocumentRoot(String documentRoot) {
		this.documentRoot = documentRoot;
	}

	/**
	 * @return the directoryIndex
	 */
	public List<String> getDirectoryIndex() {
		return DirectoryIndex;
	}

	/**
	 * @param directoryIndex the directoryIndex to set
	 */
	public void setDirectoryIndex(List<String> directoryIndex) {
		DirectoryIndex = directoryIndex;
	}

	/**
	 * @return the error400page
	 */
	public String getError400page() {
		return error400page;
	}

	/**
	 * @param error400page the error400page to set
	 */
	public void setError400page(String error400page) {
		this.error400page = error400page;
	}

	/**
	 * @return the error403page
	 */
	public String getError403page() {
		return error403page;
	}

	/**
	 * @param error403page the error403page to set
	 */
	public void setError403page(String error403page) {
		this.error403page = error403page;
	}

	/**
	 * @return the error404page
	 */
	public String getError404page() {
		return error404page;
	}

	/**
	 * @param error404page the error404page to set
	 */
	public void setError404page(String error404page) {
		this.error404page = error404page;
	}

	/**
	 * @return the error500page
	 */
	public String getError500page() {
		return error500page;
	}

	/**
	 * @param error500page the error500page to set
	 */
	public void setError500page(String error500page) {
		this.error500page = error500page;
	}

	/**
	 * @return the error501page
	 */
	public String getError501page() {
		return error501page;
	}

	/**
	 * @param error501page the error501page to set
	 */
	public void setError501page(String error501page) {
		this.error501page = error501page;
	}

	/**
	 * @return the mimeFile
	 */
	public String getMimeFile() {
		return mimeFile;
	}

	/**
	 * @param mimeFile the mimeFile to set
	 */
	public void setMimeFile(String mimeFile) {
		this.mimeFile = mimeFile;
	}

	/**
	 * @return the mimeTypes
	 */
	public MimeTypeList getMimeTypes() {
		return mimeTypes;
	}

	/**
	 * @param mimeTypes the mimeTypes to set
	 */
	public void setMimeTypes(MimeTypeList mimeTypes) {
		this.mimeTypes = mimeTypes;
	}
}
