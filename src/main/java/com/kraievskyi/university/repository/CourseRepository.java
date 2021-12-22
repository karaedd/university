package com.kraievskyi.university.repository;

import com.kraievskyi.university.model.Course;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {
    Optional<Course> findByName(String name);
}
