package com.anonplus;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.*;

public class ReadConfigFile {
	public Integer port = null;
	public String config_filename = null;
	public String path = null;
	
	
	
	public ReadConfigFile(String filename) 
	{
		try {
			 
		    File fXmlFile = new File(filename);
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(fXmlFile);
		    doc.getDocumentElement().normalize();
		 
		    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		    NodeList nodeList = doc.getElementsByTagName("virtual");		    
		    
		    System.out.println("-----------------------");
		 
		    for (int i = 0; i < 1 /* nodeList.getLength() */; ++i) {
		 
		       Node node = nodeList.item(i);	    
		       if (node.getNodeType() == Node.ELEMENT_NODE) {
		 
		          Element ele = (Element) node;
		          port = Integer.parseInt( getTagValue("port" , ele) );
		          path = new File(getTagValue("path" , ele)).getAbsolutePath();
		          
//		 
//		          System.out.println("Port : "  + Integer.toString(port));
//		          System.out.println("Path: "  + path);
		 
		        }
		    }
		  } catch (Exception e) {
			  e.printStackTrace();
		  }	
		
	}
	
	 private static String getTagValue(String sTag, Element eElement){
		    NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		    Node nValue = (Node) nlList.item(0);		 
		    return nValue.getNodeValue();    
	 }	
}
