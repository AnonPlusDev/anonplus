package com.anonplus;

import java.io.*;
import java.util.*;
/**
 * Hello world!
 *
 */


public class App  { 
	
	public static void main(String[] args) {
		//System.out.println (args[0]);
	    //start server on port x, default 80
	    //use argument to main for what port to start on
		 System.out.println ("Trying to ignite");
		ReadConfigFile cFile = new ReadConfigFile("config.xml");
		
		//System.out.println("grumpy/homePage: " + prefs.node("grumpy").get("homePage", null));		 
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
