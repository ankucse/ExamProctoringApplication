package com.exam.proctoring.service;

import com.exam.proctoring.domain.Attempt;
import com.exam.proctoring.domain.Answer;

public interface AttemptService {
    Attempt startExam(String userId, String examId);
    Attempt submitExam(String attemptId);
    Answer saveAnswer(String attemptId, String questionId, String response);
}
