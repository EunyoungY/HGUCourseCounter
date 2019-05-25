package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Student { 
	private String studentId;
	private ArrayList<Course> coursesTaken;
	private HashMap<String,Integer> semesterByYearAndSemester;  
	 
	public Student(String studentId) { 
		this.studentId  = studentId;
		this.coursesTaken = new ArrayList<Course>();
		this.semesterByYearAndSemester = new HashMap<String,Integer>();
	}
	
	public String getStudentId() {
		return this.studentId;
	}
	
	public void addCourse(Course newRecord) { 
		coursesTaken.add(newRecord);
	}

	public HashMap<String,Integer> getSemestersByYearAndSemester(){  
		String key = null;
		int value = 0;
		for(Course acourse: coursesTaken) {
			//key yearTaken+"-"+semesterCourseTaken
			//    2017-3  3
			//    2018-1  4
			key = acourse.getYearSemester();
			
			if(semesterByYearAndSemester.containsKey(key))
				continue;
			else
				semesterByYearAndSemester.put(key, ++value); 
		}  
		return semesterByYearAndSemester; 
	}
	
	 
	public int getNumCourseInNthSemester(int semester) {
		//input: 7번째 학기
		//output: 7번째 학기에 들은 수업 개수
		String key;
		int numOfCourses=0;
		Set set = semesterByYearAndSemester.keySet();
		Iterator iterator=set.iterator();

		for(Course acourse: coursesTaken) {
			key = acourse.getYearSemester(); 
			if(semester == semesterByYearAndSemester.get(key)) {
				++numOfCourses;
			}
			
		}  
		return numOfCourses;  
	} 	
}
