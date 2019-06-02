package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils { 
	
	public static ArrayList<String> getLines(String file, boolean removeHeader) {
		ArrayList<String> lines = new ArrayList<String>();  	
		String aLine=null;
		try {
			Reader in = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().withTrim().parse(in);
			for(CSVRecord record : records) {
				aLine = record.get(0)+","+record.get(1)+","+record.get(2)+","+record.get(3)+","+record.get(4)+","+record.get(5)+","+record.get(6)+","+record.get(7)+","+record.get(8);
				lines.add(aLine);
				//System.out.println(aLine);
			}
		} catch(FileNotFoundException e) {
			System.out.println("Error opening the file " + file);
			System.exit(0);
		} catch (IOException e) { 
			System.exit(0); 
			}		   
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
