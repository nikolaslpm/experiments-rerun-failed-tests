package com.lpm.experiments;

import io.github.artsok.ParameterizedRepeatedIfExceptionsTest;
import io.github.artsok.RepeatedIfExceptionsTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.RetryingTest;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RetryFailedTestsUsingArtsok {

    AtomicLong atomicLong = new AtomicLong();

    @RepeatedIfExceptionsTest(name = "Rerun failed test. Attempt {currentRepetition} of {totalRepetitions}")
    void shouldFailed() {
        System.out.println("Running test " + atomicLong.incrementAndGet());
        assertTrue(new Random().nextBoolean());
    }

    @ParameterizedRepeatedIfExceptionsTest(repeats = 5)
    @ValueSource(booleans = {true, false, true})
    void shouldRunMultipleTests(boolean expectTestToPass) {
        assertEquals(new Random().nextBoolean(), expectTestToPass);
    }

    /**
     * it's often caused by some infrastructure problems: network congestion, garbage collection etc.
     * These problems usually pass after some time. Use suspend option
     */
    @RepeatedIfExceptionsTest(repeats = 3, exceptions = IOException.class, suspend = 5000L)
    void reRunTestWithSuspendOption() throws IOException {
        if(new Random().nextBoolean()) {
            throw new IOException("Exception in I/O operation");
        }
    }
}
