package edu.handong.analysis;

import java.io.File;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import  org.apache.commons.csv.CSVFormat;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;


public class HGUCoursePatternAnalyzer {

	private HashMap<String, Student> students;  
	String input;
	String output;
	int analysis;
	String courseCode;
	int startYear;
	int endYear;
	boolean help;

	/**
	 * This method runs our analysis logic to save the number courses taken by each
	 * student per semester in a result file. Run method must not be changed!!
	 * 
	 * @param args
	 */
	public void run(String[] args) { 
		Options options = createOptions(); 
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			 
			ArrayList<String> lines = Utils.getLines(input, true);

//			students = loadStudentCourseRecords(lines);

	
 	  
			if (analysis==1){  	
				students = loadStudentCourseRecords(lines);
				Map<String, Student> sortedStudents = new TreeMap<String, Student>(students); 
				// Generate result lines to be saved.
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);

				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, output);
				return;
			} 
			 
			else if(analysis==2) {
				students = loadStudentCourseRecords(lines);
				Map<String, Student> sortedStudents = new TreeMap<String, Student>(students); 
				// Generate result lines to be saved.
				ArrayList<String> linesToBeSaved = countRateOfStudentsTakenInEachSemester(sortedStudents);

				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, output);
				return;
			}
		}
	}
	 
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);
 
			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o"); 
			analysis = Integer.parseInt(cmd.getOptionValue("a"));
			courseCode = cmd.getOptionValue("c");
			startYear = Integer.parseInt(cmd.getOptionValue("s"));
			endYear = Integer.parseInt(cmd.getOptionValue("e"));		
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();
 
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
 
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()     
				.argName("Output path")
				.required()  
				.build());
		
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
 
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()     
				.argName("course code") 
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
 
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()     
				.argName("End year for analysis")
				.required()  
				.build());
		 
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Show a Help page")
				.argName("Help")
		        .build());
		  
		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a
	 * student id and the corresponding object is an instance of Student. The
	 * Student instance have all the Course instances taken by the student.
	 * 
	 * @param lines
	 * @return
	 */
	
	

	
	private HashMap<String, Student> loadStudentCourseRecords(ArrayList<String> lines) {
		students = new HashMap<String, Student>();
		Student newStudent;
		Course newCourse; 
		
		for (String line : lines) {
			String studentId = line.split(",")[0].trim();
			newCourse = new Course(line);
			int a = Integer.parseInt(line.split(",")[7]);
			if(a>=startYear && a<=endYear) {
//				System.out.println(a); 
				if (students.containsKey(studentId)) {
					students.get(studentId).addCourse(newCourse);
				} else {
					newStudent = new Student(studentId);
					students.put(studentId, newStudent);
					newStudent.addCourse(newCourse);
				}
			}
		} 

		return students; // do not forget to return a proper variable.
	}

/*	private HashMap<String, Student> loadStudentCourseRecords2(ArrayList<String> lines) {
		students = new HashMap<String, Student>();
		Student newStudent;
		Course newCourse; 
		
		for (String line : lines) {
			//String studentId = line.split(",")[0].trim();
			//newCourse = new Course(line);
			int year = Integer.parseInt(line.split(",")[7]);
			int semester = Integer.parseInt(line.split(",")[8]);
			String courseCodeData = line.split(",")[4];
			String courseName= line.split(",")[5];
			
			for(int y=startYear; y<=endYear; y++) {
				if(courseCode.equals(courseCodeData)) {
					System.out.println(y+","+semester+","+courseCode+","+courseName);
				}  
				if (students.containsKey(year)) {
					System.out.println("**");
					//students.get(studentId).addCourse(newCourse);
				} 
//				else {
//					newStudent = new Student(studentId);
//					students.put(studentId, newStudent);
//					newStudent.addCourse(newCourse);
//				}
			}
		} 

		return students; // do not forget to return a proper variable.
	}*/
	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */

	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> result = new ArrayList<String>();
		String studentId, totalSemesters, semester, numCoursesTakenInTheSemester; 
		int year;
		
		for(String key: sortedStudents.keySet()) {
//			year = Integer.parseInt(sortedStudents.get(key).get)
//			year = sortedStudents.get(key).getSemestersByYearAndSemester();
			studentId = String.valueOf(key);
					//String.format("%04d", Integer.parseInt(key));  
			totalSemesters = Integer.toString(sortedStudents.get(key).getSemestersByYearAndSemester().size());
			for(int i=1; i<=Integer.parseInt(totalSemesters); i++) {
				numCoursesTakenInTheSemester = String.valueOf(sortedStudents.get(key).getNumCourseInNthSemester(i)); 

				System.out.println(studentId+","+totalSemesters+","+i+","+numCoursesTakenInTheSemester);
				
//				if(year==Integer.parseInt(startYear)&& endYear==0) {
//					continue;
//				}
//				else 
					result.add(studentId+","+totalSemesters+","+i+","+numCoursesTakenInTheSemester);
			}
		}
				// TODO: Implement this method
		return result; // do not forget to return a proper variable.
	}

 
	
	
//	Year,Semester,CouseCode, CourseName,TotalStudents,StudentsTaken,Rate
//	2008,1,ITP20006,Java Programming,30,10,33.3%
//	2009,1,ITP20006,Java Programming,50,25,50.0%
	private ArrayList<String> countRateOfStudentsTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> result = new ArrayList<String>();
		
		for(int i=startYear; i<=endYear; i++) {
			for(int j=1; j<=4; j++) {
				String data = Integer.toString(i)+","+Integer.toString(j);
				System.out.println(data);
				result.add(data);
			}
		}
		 
		 
				// TODO: Implement this method
		return result; // do not forget to return a proper variable.
	}
}
