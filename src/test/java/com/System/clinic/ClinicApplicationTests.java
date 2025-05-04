package com.System.clinic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // Adicione esta anotação
class ClinicApplicationTests {
    @Test
    void contextLoads() {
    }
}