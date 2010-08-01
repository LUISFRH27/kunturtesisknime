package org.unicen.edu.ar.knime.acp.kernel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Vector;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;



public class XStreamManager {

	public void SaveXMLFile(String configFile,SettingsConfig settingConfig) throws IOException
	{
		 XStream xStream = new XStream(new DomDriver());         
         FileWriter fw = null;
         try {      
			fw = new FileWriter(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter saveFile = new PrintWriter(bw);
         saveFile.append(xStream.toXML(settingConfig));
         saveFile.close();
         
         
	}
	
	
	
	public SettingsConfig GetonfigSettings(String fileName)
	{
		  FileReader file = null;
		try {
			file = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         Reader reader = new BufferedReader(file);
         XStream xStream = new XStream(new DomDriver());
         SettingsConfig settingConfig =(SettingsConfig) xStream.fromXML(reader);
         return settingConfig;
	}
	
	
}
