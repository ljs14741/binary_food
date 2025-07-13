package com.example.food;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoodApplicationTests {

    @Disabled("Jenkins 빌드시 오류 방지용")
    @Test
    void contextLoads() {
    }

}
