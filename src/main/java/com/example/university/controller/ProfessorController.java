/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.university.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.example.university.model.Professor;
import com.example.university.model.Course;
import com.example.university.service.ProfessorJpaService;

@RestController
public class ProfessorController{
    @Autowired
    private ProfessorJpaService professorService;

    @GetMapping("/professors")
    public ArrayList<Professor> getProfessors(){
        return professorService.getProfessors();
    }

    @PostMapping("/professors")
    public Professor addProfessor(@RequestBody Professor professor){
        return professorService.addProfessor(professor);
    }

    @GetMapping("/professors/{professorId}")
    public Professor getProfessorById(@PathVariable("professorId") int professorId){
        return professorService.getProfessorById(professorId);
    }

    @PutMapping("/professors/{professorId}")
    public Professor updateProfessor(@PathVariable("professorId")  int professorId, @RequestBody Professor professor){
        return professorService.updateProfessor(professorId, professor);
    }

    @DeleteMapping("/professors/{professorId}")
    public void deleteProfessor(@PathVariable("professorId") int professorId){
        professorService.deleteProfessor(professorId);
    }

    @GetMapping("/professors/{professorId}/courses")
    public List<Course> getProfessorCourses(@PathVariable("professorId") int professorId){
        return professorService.getProfessorCourses(professorId);
    }
}