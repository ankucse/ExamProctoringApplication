package com.exam.proctoring.repository;

import com.exam.proctoring.domain.Exam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Exam entity.
 */
@Repository
public interface ExamRepository extends MongoRepository<Exam, String> {
}
