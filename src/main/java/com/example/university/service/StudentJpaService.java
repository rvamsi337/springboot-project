package com.example.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
  
import java.util.*;

import com.example.university.model.Student;
import com.example.university.model.Course;
import com.example.university.repository.StudentJpaRepository;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.StudentRepository;

@Service
public class StudentJpaService implements StudentRepository{
    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Override
    public ArrayList<Student> getStudents(){
        List<Student> studentsList=studentJpaRepository.findAll();
        ArrayList<Student> students=new ArrayList<>(studentsList);
        return students;
    }

    @Override
    public Student addStudent(Student student){
        try{
            //Get completeCourses by the courseIds and validate if they exists in the courseTable
            List<Integer> courseIds=new ArrayList<>();
            for(Course course:student.getCourses()){
                courseIds.add(course.getCourseId());
            }
            List<Course> completeCourses=courseJpaRepository.findAllById(courseIds);
            if(completeCourses.size() != courseIds.size()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            //setting completeCourses to the student
            student.setCourses(completeCourses);
            studentJpaRepository.save(student);

            //Adding the student to the each completeCourse
            for(Course course:completeCourses){
                course.getStudents().add(student);
            }
            courseJpaRepository.saveAll(completeCourses);
            return student;

        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    } 
    @Override
    public Student getStudentById(int studentId){
        try{
            Student student=studentJpaRepository.findById(studentId).get();
            return student;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }  
    @Override
    public Student updateStudent(int studentId, Student student){
        try{
            Student oldStudent=studentJpaRepository.findById(studentId).get();
            if(student.getStudentName() != null){
                oldStudent.setStudentName(student.getStudentName());
            }
            if(student.getEmail() != null){
                oldStudent.setEmail(student.getEmail());
            }
            if(student.getCourses() != null){
                //get the completeCourses by using the courseIds
                List<Integer> courseIds=new ArrayList<>();
                for(Course course:student.getCourses()){
                    courseIds.add(course.getCourseId());
                }
                List<Course> completeCourses=courseJpaRepository.findAllById(courseIds);
                if(courseIds.size() != completeCourses.size()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                //Removing the old student from the existing courses
                for(Course oldCourse:oldStudent.getCourses()){
                    oldCourse.getStudents().remove(oldStudent);
                }
                courseJpaRepository.saveAll(oldStudent.getCourses());

                //setting the completeCourses to the old Student
                oldStudent.setCourses(completeCourses);
                studentJpaRepository.save(oldStudent);

                //for each complete course add the old student
                for(Course completeCourse:completeCourses){
                    completeCourse.getStudents().add(oldStudent);
                }
                courseJpaRepository.saveAll(completeCourses);
            }
            studentJpaRepository.save(oldStudent);
            return oldStudent;
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public void deleteStudent(int studentId){
        try{
            Student student=studentJpaRepository.findById(studentId).get();
            //remove the student from the courses 
            for(Course course:student.getCourses()){
                course.getStudents().remove(student);
            }
            courseJpaRepository.saveAll(student.getCourses());
            studentJpaRepository.deleteById(studentId);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Course> getStudentCourses(int studentId){
        try{
            Student student=studentJpaRepository.findById(studentId).get();
            List<Course> studentCourses=student.getCourses();
            return studentCourses;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}