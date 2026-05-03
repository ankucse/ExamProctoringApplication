package com.exam.proctoring.repository;

import com.exam.proctoring.domain.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Answer entity.
 */
@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {
    
    /**
     * Finds all answers for a given attempt.
     * @param attemptId The attempt ID.
     * @return List of answers.
     */
    List<Answer> findByAttemptId(String attemptId);
    
    /**
     * Finds a specific answer by attempt and question.
     * @param attemptId The attempt ID.
     * @param questionId The question ID.
     * @return Optional of Answer.
     */
    Optional<Answer> findByAttemptIdAndQuestionId(String attemptId, String questionId);
}
