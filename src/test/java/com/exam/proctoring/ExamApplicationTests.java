package com.exam.proctoring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.data.mongodb.uri=mongodb://localhost:27017/test-exam-platform")
class ExamApplicationTests {

    @Test
    void contextLoads() {
        // Just verify context loads
    }

}
