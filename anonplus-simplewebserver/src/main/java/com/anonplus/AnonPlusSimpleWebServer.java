package com.anonplus;

/**
 * Anonplus-simplewebserver 
 *
 */

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

public class AnonPlusSimpleWebServer  {
	private static Options options = null; // Command line options	
	private CommandLine cmd = null; // Command Line arguments
	private static String configFile = "anonplus-simplewebserver.conf";
	private static String programVersion = "0.2";
	
	
	static{
		options = new Options();
		options.addOption("h", "help", false, 
				"Shows the help");
		options.addOption("c", "config", true, "Select the configuration file");
		options.addOption("v", "version", false, 
		"Shows the version of the program");
		
	}
	
	public static void main(String[] args) {

		AnonPlusSimpleWebServer program = new AnonPlusSimpleWebServer();
		program.loadArgs(args);		
		SimpleWebServerConfig config = new SimpleWebServerConfig();
		try {
			
			config.readConfigurationFile(configFile);
			PropertyConfigurator.configure(config.getLog4jConfigFileLocation()); // Init log4j configuration.
			Global.logInfoMessage("--------------------------------------------------");
			Global.logInfoMessage("Server config: ");
			Global.logInfoMessage("DirectoryIndex: " + config.getDirectoryIndex()) ;
			Global.logInfoMessage("Port: " + config.getPort()) ;
			Global.logInfoMessage("DocumentRoot: " + config.getDocumentRoot());
			
			Server server = new Server(config);
			server.startServer();
			
		} catch (Exception e2) {
			Global.message("error:" + e2.getMessage());
			//e2.printStackTrace();
		};	    
	}
	
	
	private static void help(String pName) {
		Global.message(pName + " Version - 0.1");		
	}
	
	private void loadArgs(String[] args){
		CommandLineParser parser = new PosixParser();
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.err.println("Error parsing arguments");
			e.printStackTrace();
			System.exit(1);
		}
		
		if(cmd.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar this_jar.jar", options);
			System.exit(1);
		}
		if(cmd.hasOption("v")) {
			Global.message("AnonPlus - Simple Web Server Version " + programVersion );
			System.exit(1);
		}
		
		if(cmd.hasOption("c")) {
			configFile = cmd.getOptionValue("c");
		}
		
	}	
}

