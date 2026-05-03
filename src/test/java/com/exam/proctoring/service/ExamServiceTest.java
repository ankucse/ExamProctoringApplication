package com.exam.proctoring.service;

import com.exam.proctoring.domain.Exam;
import com.exam.proctoring.dto.ExamDTO;
import com.exam.proctoring.mapper.ExamMapper;
import com.exam.proctoring.repository.ExamRepository;
import com.exam.proctoring.service.impl.ExamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;

    @Mock
    private ExamMapper examMapper;

    @InjectMocks
    private ExamServiceImpl examService;

    @Test
    void testCreateExam() {
        ExamDTO examDTO = ExamDTO.builder().title("Test Exam").duration(60).build();
        when(examRepository.save(any(Exam.class))).thenAnswer(i -> i.getArguments()[0]);
        when(examMapper.toDTO(any())).thenReturn(examDTO);

        ExamDTO result = examService.createExam(examDTO, "admin@test.com");
        
        assertNotNull(result);
        verify(examRepository, times(1)).save(any(Exam.class));
    }

    @Test
    void testGetExamNotFound() {
        when(examRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> examService.getExam("invalidId"));
    }
}
