package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import timer.Counter;
import timer.ScheduledCounter;
import timer.Timer;

class TestCounter {
	
	Timer dummyTimer = mock(Timer.class);
	
	@Test
	void shouldCount() {
		ScheduledCounter counter = new Counter(dummyTimer);
		counter.countUp();
		assertTrue(counter.isCounting());
	}
	
	@Test
	void shouldStopCounter() {
		ScheduledCounter counter = new Counter(dummyTimer);
		counter.countUp();
		counter.stop();
		assertFalse(counter.isCounting());
	}
	
	@Test
	void shouldCountDown() {
		ScheduledCounter counter = new Counter(dummyTimer);
		counter.countDown(5);
		assertTrue(counter.isCounting());
	}

}
