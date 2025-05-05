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
import com.example.university.model.Professor;
import com.example.university.model.Course;
public interface ProfessorRepository{
    ArrayList<Professor> getProfessors();
    Professor addProfessor(Professor professor);
    Professor getProfessorById(int professorId);
    Professor updateProfessor(int professorId, Professor professor);
    void deleteProfessor(int professorId);
    List<Course> getProfessorCourses(int professorId);
}