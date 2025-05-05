/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
  
import java.util.*;

import com.example.university.model.Professor;
import com.example.university.model.Course;
import com.example.university.repository.ProfessorJpaRepository;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.ProfessorRepository;

@Service
public class ProfessorJpaService implements ProfessorRepository{
    @Autowired
    private ProfessorJpaRepository professorJpaRepository;

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Override
    public ArrayList<Professor> getProfessors(){
        List<Professor> professorsList=professorJpaRepository.findAll();
        ArrayList<Professor> professors=new ArrayList<>(professorsList);
        return professors;
    }

    @Override
    public Professor addProfessor(Professor professor){
    
        professorJpaRepository.save(professor);
        return professor;
    
    }

    @Override
    public Professor getProfessorById(int professorId){
        try{
            Professor professor=professorJpaRepository.findById(professorId).get();
            return professor;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Professor updateProfessor(int professorId, Professor professor){
        try{
            Professor oldProfessor=professorJpaRepository.findById(professorId).get();
            if(professor.getProfessorName() != null){
                oldProfessor.setProfessorName(professor.getProfessorName());
            }
            if(professor.getDepartment() != null){
                oldProfessor.setDepartment(professor.getDepartment());
            }
            professorJpaRepository.save(oldProfessor);
            return oldProfessor;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProfessor(int professorId){
        try{
            Professor professor=professorJpaRepository.findById(professorId).get();
            List<Course> courses=professor.getCourses();
            for(Course course:courses){
                course.setProfessor(null);
            }
            courseJpaRepository.saveAll(courses);
            professorJpaRepository.deleteById(professorId);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Course> getProfessorCourses(int professorId){
        try{
            Professor professor=professorJpaRepository.findById(professorId).get();
            List<Course> courses=professor.getCourses();
            return courses;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
