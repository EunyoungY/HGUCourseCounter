package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils { 

	public static ArrayList<String> getLines(String file, boolean removeHeader) {
		ArrayList<String> lines = new ArrayList<String>();
		String fileName = file;
		Scanner inputStream = null;
		try {
			inputStream = new Scanner (new File(fileName));
			if(removeHeader==true) {
				String firstline = inputStream.nextLine(); //remove Header
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}  
		while(inputStream.hasNextLine()) {  
			lines.add(inputStream.nextLine());  
		} 
		inputStream.close();
 
		return lines;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		String fileName = targetFileName;
		//String fileName = "logs/targetFileName";
		if(targetFileName.contains("/")) {
		//if(fileName.contains("/")) {
			File file = new File(fileName); 
			if(!file.exists()) file.getParentFile().mkdirs();
		}
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream(fileName,false));
			for(String line: lines) {
				outputStream.println(line);
			} 
			
		} catch(FileNotFoundException e) {
			System.out.println("Error opening the file "+ fileName);
			System.exit(0);
		}

	}
}
