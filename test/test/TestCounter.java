package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.jupiter.api.Test;

import timer.Counter;
import timer.Timer;

class TestCounter {
	
	Timer timer = mock(Timer.class);
	
	@Test
	void shouldCount() {
		Counter counter = new Counter(timer);
		counter.start();
		assertTrue(counter.isCounting());
	}
	
	@Test
	void shouldStopCounter() {
		Counter counter = new Counter(timer);
		counter.start();
		counter.stop();
		assertFalse(counter.isCounting());
	}
	
	@Test
	void shouldCountDown() {
		Counter counter = new Counter(timer, 5);
		counter.start();
		assertTrue(counter.isCounting());
	}

}
