package test.counter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static test.counter.FastCounter.DURATION_MILLISECONDS;

import java.util.concurrent.RejectedExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timer.Timer;
import timer.counter.ScheduledCounter;

class TestCounter {

	Timer mockTimer = mock(Timer.class);

	ScheduledCounter counter;

	@BeforeEach
	void setup() {
		counter = new FastCounter();
		counter.setTimer(mockTimer);
	}

	@Test
	void shouldCountUp() {
		counter.countUp();
		wait(1.5);

		assertTrue(counter.isRunning());
		verify(mockTimer, times(1)).showTime();
	}

	@Test
	void shouldStopCounter() {
		counter.countUp();
		counter.stop();
		assertFalse(counter.isRunning());
	}

	@Test
	void shouldCountDown() {
		counter.count(1);
		wait(1.5);

		assertFalse(counter.isRunning());
		verify(mockTimer, times(1)).showTime();
		// TODO this should also actually change state to idle ? or something
	}

	@Test
	void shouldCountOnlyOnce_IfStartedTwice() {
		counter.countUp();
		counter.countUp();
		wait(1.5);

		verify(mockTimer, times(1)).showTime();
	}
	
	@Test
	void shouldStopCountingDownAfterTimePasses() {
		counter.count(1);
		wait(1.5);

		assertFalse(counter.isRunning());
		verify(mockTimer, times(1)).showTime();
	}
	
	@Test
	void shouldNotThrowExceptionToStartAfterStopping() {
		counter.countUp();
		counter.stop();
	}


	private void wait(double seconds) {
		try {
			
			Thread.sleep((long) (seconds * DURATION_MILLISECONDS));
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
