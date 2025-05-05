package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
  
import java.util.*;

import com.example.university.model.*;
import com.example.university.repository.*;

@Service
public class CourseJpaService implements CourseRepository{
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private ProfessorJpaRepository professorJpaRepository;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Override
    public ArrayList<Course> getCourses(){
        List<Course> coursesList=courseJpaRepository.findAll();
        ArrayList<Course> courses=new ArrayList<>(coursesList);
        return courses;
    }

    @Override
    public Course addCourse(Course course){
        try{
            //get complete professor
            Professor professor=course.getProfessor();
            Professor completeProfessor=professorJpaRepository.findById(professor.getProfessorId()).get();
            //set the complete professor to the given course
            course.setProfessor(completeProfessor);
           

            //get Complete Students and check if they exists in the students table
            List<Integer> studentIds=new ArrayList<>();
            for(Student student: course.getStudents()){
                studentIds.add(student.getStudentId());
            }
            List<Student> completeStudents=studentJpaRepository.findAllById(studentIds);
            if(studentIds.size() != completeStudents.size()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //set the completeStudents to the course
            course.setStudents(completeStudents);
            courseJpaRepository.save(course);

            //for each complete student add the course
            for(Student student: completeStudents){
                student.getCourses().add(course);
            }
            studentJpaRepository.saveAll(completeStudents);
            return course;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course getCourseById(int courseId){
        try{
            Course course=courseJpaRepository.findById(courseId).get();
            return course;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Course updateCourse(int courseId, Course course){
        try{
            Course oldCourse=courseJpaRepository.findById(courseId).get();
            if(course.getCourseName()!=null){
                oldCourse.setCourseName(course.getCourseName());
            }
            if(course.getCredits()!=0){
                oldCourse.setCredits(course.getCredits());
            }
            if(course.getProfessor() != null){
                //get professor id and find complete Professor
                Professor completeProfessor=professorJpaRepository.findById(course.getProfessor().getProfessorId()).get();
                //set the completeProfessor to the oldCourse
                oldCourse.setProfessor(completeProfessor);

            }
            if(course.getStudents() != null){
                
                //Get studentIds and find the completeStudents, Also check if they exists in the table
                List<Integer> studentIds=new ArrayList<>();
                for(Student student:course.getStudents()){
                    studentIds.add(student.getStudentId());
                }
                List<Student> completeStudents=studentJpaRepository.findAllById(studentIds);
                if(studentIds.size() != completeStudents.size()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                //Removing old course from the old course students
                for(Student oldStudent:oldCourse.getStudents()){
                    oldStudent.getCourses().remove(oldCourse);
                }
                studentJpaRepository.saveAll(oldCourse.getStudents());

                //setting completeStudents to the oldCourse
                oldCourse.setStudents(completeStudents);
                courseJpaRepository.save(oldCourse);

                //for each completeStudent add the oldCourse
                for(Student student:completeStudents){
                    student.getCourses().add(oldCourse);
                }
            }
            courseJpaRepository.save(oldCourse);
            return oldCourse;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public void deleteCourse(int courseId){
        try{
            Course course=courseJpaRepository.findById(courseId).get();
            //remove this course from the professor table
            Professor professor=course.getProfessor();
            if (professor != null) {
                professor.getCourses().remove(course);
                professorJpaRepository.save(professor);
            }

            //remove this course from the students
            for(Student student:course.getStudents()){
                student.getCourses().remove(course);
            }
            studentJpaRepository.saveAll(course.getStudents());

            //delete the course
            courseJpaRepository.deleteById(courseId);
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Professor getCourseProfessor(int courseId){
        try{
            Course course=courseJpaRepository.findById(courseId).get();
            Professor professor=course.getProfessor();
            return professor;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Student> getCourseStudents(int courseId){
        try{
            Course course=courseJpaRepository.findById(courseId).get();
            List<Student> students=course.getStudents();
            return students;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
    
}