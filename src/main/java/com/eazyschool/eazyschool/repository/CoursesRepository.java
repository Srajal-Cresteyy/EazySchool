package com.eazyschool.eazyschool.repository;

import com.eazyschool.eazyschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {
    public List<Courses> findByOrderByNameDesc();
    public List<Courses> findByOrderByName();
}
