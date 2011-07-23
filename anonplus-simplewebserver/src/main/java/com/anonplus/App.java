package com.anonplus;

import java.io.*;
import java.util.*;
/**
 * Anonplus-simplewebserver 
 *
 */


public class App  { 
	static private void message(String m)
	{
		System.out.println (m);
	}	
	
	public static void main(String[] args) {


		System.out.println ("Trying to ignite");
		ReadConfigFile cFile = new ReadConfigFile("config.xml");
		message("Configs:\n");
		message("directoryIndex: " + cFile.directoryIndex +"\n") ;
		message("port: " + cFile.port +"\n") ;
		message("path: " + cFile.path +"\n") ;
		
		
		Integer port;
		
		
	    try {
	      port = new Integer(args[0]);
	      //catch parse error
	    }
	    catch (Exception e) {
	      port = new Integer(80);
	    }
	    Server s = new Server(port);
	    
	}
	
}

