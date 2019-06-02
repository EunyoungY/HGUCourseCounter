package edu.handong.analysis.datamodel;
import edu.handong.analysis.HGUCoursePatternAnalyzer;

public class Course {
	private String studentId;
    private String yearMonthGraduated;
    private String firstMajor;
    private String secondMajor;
    private String courseCode;
    private String courseName;
    private String courseCredit;
    private String yearTaken;
    private String semesterCourseTaken; 
	
	public Course(String line) { 
		this.studentId = line.split(",")[0].trim();
		this.yearMonthGraduated = line.split(",")[1].trim();
		this.firstMajor = line.split(",")[2].trim();
		this.secondMajor = line.split(",")[3].trim();
		this.courseCode = line.split(",")[4].trim();
		this.courseName = line.split(",")[5].trim();
		this.courseCredit = line.split(",")[6].trim();
		this.yearTaken = line.split(",")[7].trim();
		this.semesterCourseTaken = line.split(",")[8].trim(); 
	}
	
	public String getYearSemester() { 
		String yearSemester = yearTaken+"-"+semesterCourseTaken;
		
		return yearSemester;
	}
	
	public int getYearTaken() { 
		return Integer.parseInt(this.yearTaken);
	}
	
	public int getSemesterCourseTaken() { 
		return Integer.parseInt(this.semesterCourseTaken);
	}
	  
} 
