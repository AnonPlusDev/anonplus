package com.anonplus;

import java.io.*;
import java.util.*;
/**
 * Anonplus-simplewebserver 
 *
 */


public class App  { 
	
	public static void main(String[] args) {


		 System.out.println ("Trying to ignite");
		ReadConfigFile cFile = new ReadConfigFile("config.xml");
		
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
