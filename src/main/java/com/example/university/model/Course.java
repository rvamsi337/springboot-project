package com.example.university.model;

import javax.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="course")
public class Course{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    int courseId;
    @Column(name="name")
    String courseName;
    @Column(name="credits")
    int credits;
    @ManyToOne
    @JoinColumn(name="professorid")
    Professor professor;
    @ManyToMany
    @JoinTable(
        name="course_student",
        joinColumns=@JoinColumn(name="courseid"),
        inverseJoinColumns=@JoinColumn(name="studentid")
    )
    @JsonIgnoreProperties("courses")
    List<Student> students;

    public Course(){}

    public void setCourseId(int courseId){
        this.courseId=courseId;
    }

    public int getCourseId(){
        return this.courseId;
    }

    public void setCourseName(String courseName){
        this.courseName=courseName;
    }
    public String getCourseName(){
        return this.courseName;
    }

    public void setCredits(int credits){
        this.credits=credits;
    }

    public int getCredits(){
        return this.credits;
    }

    public void setProfessor(Professor professor){
        this.professor=professor;
    }
    public Professor getProfessor(){
        return this.professor;
    }

    public void setStudents(List<Student> students){
        this.students=students;
    }
    public List<Student> getStudents(){
        return this.students;
    }
}