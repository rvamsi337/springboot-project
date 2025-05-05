/*
 *
 * You can use the following import statements
 * 
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.university.repository;
import java.util.*;
import com.example.university.model.*;

public interface CourseRepository{
    ArrayList<Course> getCourses();
    Course addCourse(Course course);
    Course getCourseById(int courseId);
    Course updateCourse(int courseId, Course course);
    void deleteCourse(int courseId);
    Professor getCourseProfessor(int courseId);
    List<Student> getCourseStudents(int courseId);
}