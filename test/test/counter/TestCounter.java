package test.counter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static test.counter.FastCounter.DURATION_MILLISECONDS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timer.Timer;
import timer.counter.ScheduledCounter;

class TestCounter {
	
	Timer mockTimer = mock(Timer.class);
	
	ScheduledCounter counter;
	
	@BeforeEach
	void setup() {
		// Fast counter works by using 0.1 * second as a measurement unit.
		// So instead of 1000 miliseconds being one second, its 100 miliseconds.
		// 10 times faster. So we use Thread.sleep(150).
		counter = new FastCounter();
		counter.setTimer(mockTimer);
	}
	
	@Test
	void shouldCountUp() throws InterruptedException {
		counter.countUp();
		Thread.sleep((long) (1.5 * DURATION_MILLISECONDS));
		
		assertTrue(counter.isCounting());
		verify(mockTimer, times(1)).showTime();
	}
	
	@Test
	void shouldStopCounter() {
		counter.countUp();
		counter.stop();
		assertFalse(counter.isCounting());
	}
	
	@Test
	void shouldCountDown() throws InterruptedException {
		counter.count(1);
		Thread.sleep((long) (1.5 * DURATION_MILLISECONDS));
		
		assertFalse(counter.isCounting());
		verify(mockTimer, times(1)).showTime();
		// TODO this should also actually change state to idle ? or something
	}
	
	@Test
	void shouldCountOnlyOnce_IfStartedTwice() throws InterruptedException {
		counter.countUp();
		counter.countUp();
		Thread.sleep((long) (1.5 * DURATION_MILLISECONDS));
		
		verify(mockTimer, times(1)).showTime();
	}

}
