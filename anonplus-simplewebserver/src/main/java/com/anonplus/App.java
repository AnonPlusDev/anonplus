package com.anonplus;

import java.io.*;
import java.util.*;
/**
 * Anonplus-simplewebserver 
 *
 */


public class App  {
	static private Server server = null;
	
	
	public static void main(String[] args) {

		
		System.out.println ("Trying to ignite");
		Config config = new Config();
		try {
			config.readConfigurationFile("anonplus-simplewebserver.conf");
			Global.message("--------------------------------------------------");
			Global.message("Server config: ");
			Global.message("DirectoryIndex: " + config.DirectoryIndex ) ;
			Global.message("Port: " + config.Port) ;
			Global.message("DocumentRoot: " + config.DocumentRoot);
			server = new Server(config);
			
		} catch (Exception e2) {
			Global.message("error:" + e2.getMessage());
		};
		
		


	    
	    
	}
	
}

