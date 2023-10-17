package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import timer.Counter;
import timer.Timer;

class TestCounter {
	
	Timer timer = mock(Timer.class);
	
	@Test
	void shouldCount() {
		Counter counter = new Counter(timer);
		counter.countUp();
		assertTrue(counter.isCounting());
	}
	
	@Test
	void shouldStopCounter() {
		Counter counter = new Counter(timer);
		counter.countUp();
		counter.stop();
		assertFalse(counter.isCounting());
	}
	
	@Test
	void shouldCountDown() {
		Counter counter = new Counter(timer);
		counter.countDown(5);
		assertTrue(counter.isCounting());
	}

}
