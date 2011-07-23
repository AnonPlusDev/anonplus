package com.anonplus;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;


//import java.text.*;

public class Server 
	extends Thread {
		
		private Integer port = null;
		
		private void message(String m)
		{
			System.out.println (m);
		}
		
		public Server(int _port) {	
		    port = _port;
		    this.startServer();
		}
		
		private void startServer()  {
			message("Starting server...");	
			ServerSocket socket = null;
			
			message("The simple httpserver v. 0000000000\nCoded by Jon Berg" +
		      "<jon.berg[on server]turtlemeat.com>\n\n");
			try {
			     
			      //s("Trying to bind to localhost on port " + Integer.toString(port) + "...");
			      //make a ServerSocket and bind it to given port,
			      socket = new ServerSocket(port);			      
			}   catch (Exception e) {
				message("Binding to port " + Integer.toString(port) + ": Failed!\n");
				message("\n" + "Fatal Error:" + e.getMessage());
			    return;
			}
			message("Binding to port " + Integer.toString(port) + ": OK!\n");
			
			while (true) {
				message("Waiting for requests!\n");
				try {
			        Socket connSocket= socket.accept();
			        InetAddress client = connSocket.getInetAddress();

			        message(client.getHostName() + " connected to server.\n");
			        //Read the http request from the client from the socket interface
			        //into a buffer.
			        BufferedReader input =
			            new BufferedReader(new InputStreamReader(connSocket.
			            getInputStream()));
			        //Prepare a outputstream from us to the client,
			        //this will be used sending back our response
			        //(header + requested file) to the client.
			        DataOutputStream output =
			            new DataOutputStream(connSocket.getOutputStream());
			        message("Got request!\n");
			        handle_request(input, output);
			        
			    }
				catch (Exception e) { 
			        message("\nError:" + e.getMessage());
			    }
			}
			
			
		}
		
		private void handle_request(BufferedReader input, DataOutputStream output) {
			String http = new String();
		
		}
}
