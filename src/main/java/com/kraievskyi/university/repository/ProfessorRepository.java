package com.kraievskyi.university.repository;

import com.kraievskyi.university.model.Professor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfessorRepository extends MongoRepository<Professor, String> {
}
