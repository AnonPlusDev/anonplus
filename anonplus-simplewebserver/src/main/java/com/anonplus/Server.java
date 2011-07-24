package com.anonplus;

import java.net.*;
import java.io.*;

public class Server extends Thread {
	public enum METHOD {
		NOT_SUPPORTED, GET, HEAD
	};


	private SimpleWebServerConfig config = null;

	private void message(String m) {
		System.out.println(m);
	}

	public Server(SimpleWebServerConfig _config) {
		config = _config;		
		this.startServer();
	}

	private void startServer() {
		message("Starting server...");
		ServerSocket socket = null;
		try {

			socket = new ServerSocket(config.Port);
		} catch (Exception e) {
			message("Binding to port " + Integer.toString(config.Port) + ": Failed!\n");
			message("\n" + "Fatal Error:" + e.getMessage());
			return;
		}
		message("Binding to port " + Integer.toString(config.Port) + ": OK!\n");

		while (true) {
			message("Waiting for requests!\n");
			try {
				Socket connSocket = socket.accept();
				InetAddress client = connSocket.getInetAddress();

				message(client.getHostName() + " connected to server.\n");

				BufferedReader input = new BufferedReader(
						new InputStreamReader(connSocket.getInputStream()));

				DataOutputStream output = new DataOutputStream(connSocket
						.getOutputStream());
				message("Got request!\n");
				handle_request(input, output);

			} catch (Exception e) {
				message("\nError:" + e.getMessage());
			}
		}

	}

	private void handle_request(BufferedReader input, DataOutputStream output) {

		METHOD method = null;
		String http = new String();
		String path = new String(); // the various things, what http v, what
		String contentType = null;
		// path,
		String file = new String(); // what file
		String user_agent = new String();

		try {
			String tmp = input.readLine();
			String tmp2 = new String(tmp);
			tmp.toUpperCase();
			if (tmp.startsWith("GET")) {
				method = METHOD.GET;
			} else if (tmp.startsWith("HEAD")) {
				method = METHOD.HEAD;
			} else {
				method = METHOD.NOT_SUPPORTED;
			}

			if (method == METHOD.NOT_SUPPORTED) {
				try {
					output.writeBytes(construct_http_header(501, "text/html"));
					output.close();
					return;
				} catch (Exception e1) {
					message("error:" + e1.getMessage());
				}
			}

			int start = 0;
			int end = 0;
			for (int a = 0; a < tmp2.length(); a++) {
				if (tmp2.charAt(a) == ' ' && start != 0) {
					end = a;
					break;
				}
				if (tmp2.charAt(a) == ' ' && start == 0) {
					start = a;
				}
			}
			path = tmp2.substring(start + 2, end); // fill in the path
			// if(path == "") {
			// path = (cFile.directoryIndex);
			// TODO: make the search for directory Index
			// }

		} catch (Exception e2) {
			message("error:" + e2.getMessage());
		}

		// path do now have the filename to what to the file it wants to open
		message("\nClient requested:" + new File(path).getAbsolutePath() + "\n");
		FileInputStream requestedfile = null;
		String fullPath = config.DocumentRoot + "/" + path;
		File f = new File(fullPath);
		
		// Checks if the full path is a directory. If it is then 
		// searches directoryIndex files
		if(f.isDirectory())
		{
			for(int i = 0; i < config.DirectoryIndex.size(); ++i)
			{
				File file2 = new File(fullPath + "/" + config.DirectoryIndex.get(i));
				if(file2.exists() && file2.isFile() && file2.canRead())
				{
					fullPath = file2.getAbsolutePath();
					break;
				}
			}
		}
		try {
			// TODO: Prevent Directory Transversal			
			requestedfile = new FileInputStream(fullPath);
		} catch (Exception e) {
			try {
				// if you could not open the file send a 404
				output.writeBytes(construct_http_header(404, "text/html"));
				// close the stream
				output.close();
			} catch (Exception e2) {
				message("error" + e.getMessage());	
			}
			
		} 


		try {
			// CHECK EXTENSION
			
			String tmpList[] = path.split("\\.");
			Global.message("tmpListLenght : " + tmpList.length);
			String ext = new String();
			if(tmpList.length > 1 ) { 
				ext = tmpList[tmpList.length - 1];
				Global.message(ext);
				contentType = config.MimeTypes.findByExtension(ext).contentType;
				Global.message(contentType);
			}
			if(contentType == null)
				contentType = new String("text/html");		
			
			output.writeBytes(construct_http_header(200, contentType));


			if (method == METHOD.GET) { 
				while (true) {

					int b = requestedfile.read();
					if (b == -1) {
						break;
					}
					output.write(b);
				}

			}
			output.close();
			requestedfile.close();
		}

		catch (Exception e) {
		}

	}

	private String construct_http_header(int return_code, String _contentType) {
		String s = "HTTP/1.0 ";
		// TODO : Serve Error Pages
		switch (return_code) {
		case 200:
			s = s + "200 OK";
			break;
		case 400:
			s = s + "400 Bad Request";
			break;
		case 403:
			s = s + "403 Forbidden";
			break;
		case 404:
			s = s + "404 Not Found";
			break;
		case 500:
			s = s + "500 Internal Server Error";
			break;
		case 501:
			s = s + "501 Not Implemented";
			break;
		}
		s = s + "\r\n"; // other header fields,
		s = s + "Connection: close\r\n"; // only closed
		s = s + "Server: AnonplusSimpleWebServer v0.1\r\n"; // server name		
		s = s + "Content-Type: " + _contentType + "\r\n";
		s = s + "\r\n"; // end of the httpheader

		Global.message(s);
		return s;
	}
	
	private void outputErrorPage(int _error_code, String _contentType, DataOutputStream _output)
	{
		String s = "HTTP/1.0 ";
		// TODO : Serve Error Pages
		switch (_error_code) {
		case 200:
			s = s + "200 OK";
			break;
		case 400:
			s = s + "400 Bad Request";
			break;
		case 403:
			s = s + "403 Forbidden";
			break;
		case 404:
			s = s + "404 Not Found";
			break;
		case 500:
			s = s + "500 Internal Server Error";
			break;
		case 501:
			s = s + "501 Not Implemented";
			break;
		}
		s = s + "\r\n"; // other header fields,
		s = s + "Connection: close\r\n"; // only closed
		s = s + "Server: AnonplusSimpleWebServer v0.1\r\n"; // server name		
		s = s + "Content-Type: " + _contentType + "\r\n";
		s = s + "\r\n"; // end of the httpheader

	}
}
