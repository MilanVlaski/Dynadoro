package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timer.Counter;
import timer.ScheduledCounter;
import timer.Timer;

class TestCounter {
	
	Timer dummyTimer = mock(Timer.class);
	
	ScheduledCounter counter;
	
	@BeforeEach
	void setup() {
		counter = new Counter();
		counter.setTimer(dummyTimer);
	}
	
	@Test
	void shouldCount() {
		counter.countUp();
		assertTrue(counter.isCounting());
	}
	
	@Test
	void shouldStopCounter() {
		counter.countUp();
		counter.stop();
		assertFalse(counter.isCounting());
	}
	
	@Test
	void shouldCountDown() {
		counter.countDown(5);
		assertTrue(counter.isCounting());
	}

}
