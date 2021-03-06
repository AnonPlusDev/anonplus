package com.anonplus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

public class Server implements Runnable {
	public enum METHOD {
		NOT_SUPPORTED, GET, HEAD
	};

	private SimpleWebServerConfig config;
	private ServerSocket socket;
	private Socket clientSocket;

	public Server(SimpleWebServerConfig _config) {
		config = _config;
	}
	
	public void run() {
		listenClientRequest();
	}
	
	private void listenClientRequest() {		
		Global.logDebugMessage("Waiting for requests!\n");
		try {				
			InetAddress client = clientSocket.getInetAddress();

			Global.logInfoMessage(client.getHostName() + " connected to server with the Thread ID:" + Thread.currentThread().getId() + Constants.NEWLINE_SEPARATOR);

			BufferedReader input = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			DataOutputStream output = new DataOutputStream(clientSocket
					.getOutputStream());
			handle_request(input, output);

		} catch (Exception e) {
			Global.logErrorMessage("\nError:" + e.getMessage());
			Global.logErrorMessage("Caused by:" + e.getCause().getClass().getName());
		}		
	}	

	public void startServer() {
		Global.logInfoMessage("Starting server...");
		try {
			socket = new ServerSocket(config.getPort());
		} catch (Exception e) {
			Global.logInfoMessage("Binding to port " + Integer.toString(config.getPort()) + ": Failed!\n");
			Global.logInfoMessage("\n" + "Fatal Error:" + e.getMessage());
			return;
		}
		Global.logErrorMessage("Binding to port " + Integer.toString(config.getPort()) + ": OK!\n");
		while (true) {
			try {
				clientSocket = socket.accept();
				new Thread(this).start();
			} catch (IOException exception) {
				Global.logErrorMessage(Constants.NEWLINE_SEPARATOR + "Error accepting client requests:" + exception.getMessage());
				return;
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
			tmp = tmp.toUpperCase();
			if (tmp.startsWith(Constants.HTTP_GET_METHOD)) {
				method = METHOD.GET;
			} else if (tmp.startsWith(Constants.HTTP_HEAD_METHOD)) {
				method = METHOD.HEAD;
			} else {
				method = METHOD.NOT_SUPPORTED;
			}

			if (method == METHOD.NOT_SUPPORTED) {
				try {
					output.writeBytes(construct_http_header(501, Constants.HTML_CONTENT_TYPE));
					output.close();
					return;
				} catch (Exception e1) {
					Global.logErrorMessage("error:" + e1.getMessage());
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
			Global.logErrorMessage("error:" + e2.getMessage());
		}

		// path do now have the filename to what to the file it wants to open
		Global.logInfoMessage(Constants.NEWLINE_SEPARATOR + "Client requested:" + new File(config.getDocumentRoot() + File.separator + path).getAbsolutePath() + Constants.NEWLINE_SEPARATOR);
		FileInputStream requestedfile = null;
		String fullPath = config.getDocumentRoot() + File.separator + path;
		File f = new File(fullPath);
		
		// Checks if the full path is a directory. If it is then 
		// searches directoryIndex files
		if(f.isDirectory())
		{
			for(int i = 0; i < config.getDirectoryIndex().size(); ++i)
			{
				File file2 = new File(fullPath + "/" + config.getDirectoryIndex().get(i));
				if(file2.exists() && file2.isFile() && file2.canRead())
				{
					fullPath = file2.getAbsolutePath();
					break;
				}
			}
		}
		try {
			/**
			 * Trying to prevent Directory Transversal.
			 */
			if (!isQueryStringSafe(path, config.getDocumentRoot())) {
				Global.logWarnMessage("Request query string NOT SAFE!");
				// Sending a 404 page
				output.writeBytes(construct_http_header(404, Constants.HTML_CONTENT_TYPE));
				// close the stream
				output.close();
			}
						
			requestedfile = new FileInputStream(fullPath);
		} catch (Exception e) {
			try {
				// if you could not open the file send a 404
				output.writeBytes(construct_http_header(404, Constants.HTML_CONTENT_TYPE));
				// close the stream
				output.close();
			} catch (Exception e2) {
				Global.logErrorMessage("error" + e.getMessage());	
			}
			
		} 


		try {
			// CHECK EXTENSION
			
			String tmpList[] = path.split("\\.");
			Global.logDebugMessage("tmpListLength : " + tmpList.length);
			String ext = null;
			if(tmpList.length > 1 ) { 
				ext = tmpList[tmpList.length - 1];
				Global.logDebugMessage(ext);
				contentType = config.getMimeTypes().findByExtension(ext).getContentType();
				Global.logInfoMessage(contentType);
			}
			if(contentType == null) {
				contentType = new String(Constants.HTML_CONTENT_TYPE);
			}
			
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
	
	private boolean isQueryStringSafe (String path, String documentRoot) throws IOException {
		
		Global.logInfoMessage("Query String : " + path);
		
		/**
		 * Decoding the query string just in case, the client is encoding 
		 * the special characters.
		 */		
		path = URLDecoder.decode(path, Constants.URL_DECODE_CHARSET);
		
		/**
		 * Check to determine if user is trying to traverse through
		 * directories using the characters .. or ../ or ..\ in the URL. If so,
		 * throw error.
		 */
		if (path.matches("^.*[\\.]{2}[/\\\\]?.*$")) {
			return false; 
		}
		
		File docRoot = new File(documentRoot);
		File userFile = new File(documentRoot + File.pathSeparator + path);
		
		/**
		 * Check to determine if the user is not traversing out of the document
		 * root.
		 */
		if (userFile.getCanonicalPath().startsWith(docRoot.getCanonicalPath())) { 
			return true; 
		}
		
		return false;				
	}

	private String construct_http_header(int return_code, String _contentType) {
		StringBuilder builder = new StringBuilder(Constants.HTTP_VERSION);
		// TODO : Serve Error Pages
		switch (return_code) {
		case 200:
			builder.append(Constants.HTTP_OK);
			break;
		case 400:
			builder.append(Constants.HTTP_BAD_REQ);
			break;
		case 403:
			builder.append(Constants.HTTP_FORBIDDEN);
			break;
		case 404:
			builder.append(Constants.HTTP_URL_NOT_FOUND);
			break;
		case 500:
			builder.append(Constants.HTTP_INTERNAL_SERVER_ERROR);
			break;
		case 501:
			builder.append(Constants.HTTP_NOT_IMPLEMENTED);
			break;
		}
		builder.append(Constants.NEWLINE_SEPARATOR); // other header fields,
		builder.append("Connection: close"); // only closed
		builder.append(Constants.NEWLINE_SEPARATOR); 
		builder.append("Server: AnonplusSimpleWebServer v0.1");// server name
		builder.append(Constants.NEWLINE_SEPARATOR);  		
		builder.append("Content-Type: ");
		builder.append(_contentType);
		builder.append(Constants.NEWLINE_SEPARATOR);
		builder.append(Constants.NEWLINE_SEPARATOR); // end of the httpheader
		builder.append("Page rendered!");
		String httpHeaders = builder.toString();
		Global.logDebugMessage(httpHeaders);
		return httpHeaders;
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
