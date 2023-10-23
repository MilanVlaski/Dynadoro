package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timer.Timer;
import timer.counter.Counter;
import timer.counter.ScheduledCounter;

class TestRegularCounter {
	
	Timer dummyTimer = mock(Timer.class);
	
	ScheduledCounter counter;
	
	@BeforeEach
	void setup() {
		counter = new Counter();
		counter.setTimer(dummyTimer);
	}
	
	@Test
	void shouldCountUp() throws InterruptedException {
		counter.countUp();
		Thread.sleep(1500);
		
		assertTrue(counter.isCounting());
		verify(dummyTimer, times(1)).showTime();
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
		Thread.sleep(1500);
		
		assertFalse(counter.isCounting());
		verify(dummyTimer, times(1)).showTime();
	}

	@Test
	void should() {
		counter.countUp();
		counter.count(55);
		counter.countUp();
		counter.countUp();
		assertTrue(counter.isCounting());
	}
}
