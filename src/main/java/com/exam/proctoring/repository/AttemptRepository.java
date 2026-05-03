package com.exam.proctoring.repository;

import com.exam.proctoring.domain.Attempt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Attempt entity.
 */
@Repository
public interface AttemptRepository extends MongoRepository<Attempt, String> {
    
    /**
     * Finds all attempts made by a specific user.
     * @param userId The ID of the user.
     * @return List of attempts.
     */
    List<Attempt> findByUserId(String userId);

    /**
     * Finds an attempt for a specific user and exam.
     * @param userId User ID.
     * @param examId Exam ID.
     * @return An Optional containing the Attempt if found.
     */
    Optional<Attempt> findByUserIdAndExamId(String userId, String examId);
}
