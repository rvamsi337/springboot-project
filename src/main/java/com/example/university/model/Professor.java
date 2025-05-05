package com.example.university.model;

import javax.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name="professor")
public class Professor{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int professorId;
    @Column(name="name")
    private String professorName;
    @Column(name="department")
    private String department;
    @OneToMany(mappedBy = "professor")
    @JsonIgnoreProperties("professor") // Prevents infinite recursion
    private List<Course> courses;

    public Professor(){}
    
    public void setProfessorId(int professorId){
        this.professorId=professorId;
    }
    public int getProfessorId(){
        return this.professorId;
    }

    public void setProfessorName(String professorName){
        this.professorName=professorName;
    }
    public String getProfessorName(){
        return this.professorName;
    }

    public void setDepartment(String department){
        this.department=department;
    }
    public String getDepartment(){
        return this.department;
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    

}
