package com.exam.proctoring.service.impl;

import com.exam.proctoring.domain.Answer;
import com.exam.proctoring.domain.Attempt;
import com.exam.proctoring.domain.AttemptStatus;
import com.exam.proctoring.repository.AnswerRepository;
import com.exam.proctoring.repository.AttemptRepository;
import com.exam.proctoring.service.AttemptService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AttemptServiceImpl implements AttemptService {

    private final AttemptRepository attemptRepository;
    private final AnswerRepository answerRepository;

    public AttemptServiceImpl(AttemptRepository attemptRepository, AnswerRepository answerRepository) {
        this.attemptRepository = attemptRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Attempt startExam(String userId, String examId) {
        // Check if an attempt already exists
        Optional<Attempt> existingAttempt = attemptRepository.findByUserIdAndExamId(userId, examId);
        if (existingAttempt.isPresent()) {
            return existingAttempt.get();
        }

        Attempt attempt = Attempt.builder()
                .userId(userId)
                .examId(examId)
                .startTime(LocalDateTime.now())
                .status(AttemptStatus.IN_PROGRESS)
                .build();
        return attemptRepository.save(attempt);
    }

    @Override
    public Attempt submitExam(String attemptId) {
        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));
        
        attempt.setStatus(AttemptStatus.SUBMITTED);
        attempt.setEndTime(LocalDateTime.now());
        return attemptRepository.save(attempt);
    }

    @Override
    public Answer saveAnswer(String attemptId, String questionId, String response) {
        Optional<Answer> existingAnswer = answerRepository.findByAttemptIdAndQuestionId(attemptId, questionId);
        
        Answer answer;
        if (existingAnswer.isPresent()) {
            answer = existingAnswer.get();
            answer.setResponse(response);
        } else {
            answer = Answer.builder()
                    .attemptId(attemptId)
                    .questionId(questionId)
                    .response(response)
                    .build();
        }
        
        return answerRepository.save(answer);
    }
}
