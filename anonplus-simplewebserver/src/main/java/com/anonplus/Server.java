package com.anonplus;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;


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
			try {
			   
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

			        BufferedReader input =
			            new BufferedReader(new InputStreamReader(connSocket.
			            getInputStream()));

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
