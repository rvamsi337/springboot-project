package com.example.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.university.model.Course;

@Repository
public interface CourseJpaRepository extends JpaRepository<Course,Integer>{
    
}
