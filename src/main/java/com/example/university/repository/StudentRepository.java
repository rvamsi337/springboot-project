package com.example.university.repository;
import java.util.*;
import com.example.university.model.Student;
import com.example.university.model.Course;
public interface StudentRepository{
    ArrayList<Student> getStudents();
    Student addStudent(Student student);
    Student getStudentById(int studentId);
    Student updateStudent(int studentId, Student student);
    void deleteStudent(int studentId);
    List<Course> getStudentCourses(int studentId);
}