package com.lpm.experiments;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.RetryingTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RetryFailedTestsUsingPioneer {

    @RetryingTest(minSuccess = 1, maxAttempts = 2)
    void shouldFailed() {

        assertTrue(true);
        assertTrue(true);
    }

    //Doesn't work with '@ParameterizedTest'
    @RetryingTest(2)
    @ParameterizedTest
    @ValueSource(booleans = {true, true, true})
    void shouldRunMultipleTests(boolean expectTestToPass) {
        assertTrue(expectTestToPass);
    }

}
