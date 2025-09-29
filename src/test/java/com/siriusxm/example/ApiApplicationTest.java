package com.siriusxm.example;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mockStatic;

public class ApiApplicationTest {

    // mocks
    private final MockedStatic<SpringApplication> springApplicationMockedStatic = mockStatic(SpringApplication.class);

    @Test
    public void test_main_success() {
        // given
        String[] args = { "test" };

        // when
        ApiApplication.main(args);

        // then
        springApplicationMockedStatic.verify(() -> SpringApplication.run(ApiApplication.class, args),
                times(1)
        );

        springApplicationMockedStatic.close();
    }
}
