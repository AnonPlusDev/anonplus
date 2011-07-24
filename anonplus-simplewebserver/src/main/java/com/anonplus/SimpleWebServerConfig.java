package com.anonplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SimpleWebServerConfig {
	
	public Integer Port = null;
	public String config_filename = null;
	public String DocumentRoot = null;
	public List<String> DirectoryIndex = null;
	public String Error400page = null;
	public String Error403page = null;
	public String Error404page = null;
	public String Error500page = null;
	public String Error501page = null;
	public String MimeFile = null;
	MimeTypeList MimeTypes = null;
	
	public void readConfigurationFile(String filename) throws IOException {
		File file= new File(filename);
		filename = file.getAbsolutePath();
		if(!file.exists() ||  !file.canRead() || file.isDirectory())
		{
			throw new IOException("Path: " + filename + " does not exist, is a directory or it's not readble!\n");
		}
		Global.message("Reading " + filename + " !\n");
		 
		Properties p = new Properties();
		FileReader fr= new FileReader(filename);
		p.load(fr);
		
		this.Port = Integer.parseInt(p.getProperty("Port", "8080"));
		this.DocumentRoot = p.getProperty("DocumentRoot", "./public_html");
		this.DocumentRoot = new File(this.DocumentRoot).getAbsolutePath();		
		Global.message(" ++++++++ " + this.DocumentRoot );
		String tmpList[] = p.getProperty("DirectoryIndex", "index.html default.html").split(" ");
		this.DirectoryIndex = new ArrayList<String>();
		for(int i=0; i < tmpList.length; ++i) {
			this.DirectoryIndex.add(tmpList[i]);			
			if(Global.DEBUG) Global.message("Adding to DirectoryIndex: " + tmpList[i] );		
			
		}
		this.MimeFile = p.getProperty("MimeFile", "mime.types");
		this.MimeFile = new File(this.MimeFile).getAbsolutePath();	
		Global.message(" ++++++++ " + this.MimeFile );
		
		this.DocumentRoot = p.getProperty("DocumentRoot", "public_html");
		if(Global.DEBUG) p.list(System.out);
		fr.close();
		
		this.readMimeFile();
		
	}
	
	private void readMimeFile() throws IOException {
	
		File file= new File(this.MimeFile);		
		if(!file.exists() ||  !file.canRead() || file.isDirectory())
		{
			throw new IOException("Path: " + this.MimeFile + " does not exist, is a directory or it's not readble!\n");
		} 
		
		FileReader fr = new FileReader(this.MimeFile);
		BufferedReader br = new BufferedReader(fr);
		String bufferLine;
		Pattern p = Pattern.compile("#.*$");
		Global.message("-------------------------------------");
		Global.message("-------------------------------------");
		Global.message("-------------------------------------");
		Global.message("           REGEX                     ");
		
		MimeTypes = new MimeTypeList(); 
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
					mimeType.contentType = sList[i];
				else
					mimeType.extensions.add(sList[i]);				
			}
			MimeTypes.list.add(mimeType);
			Global.message("Adding Mime: contentType ->  " + mimeType.contentType );
			Global.message("Adding Mime: extensions ->  " + mimeType.extensions.toString() );
				
		} while(bufferLine != null);
		
		
		
		

		
	}
}
