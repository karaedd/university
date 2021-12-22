package com.kraievskyi.university.repository;

import com.kraievskyi.university.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
}
