package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;import java.math.BigDecimal;
import java.util.Calendar;

import javax.xml.datatype.Duration;
import javax.xml.datatype.DatatypeConstants.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timer.Timer;
import timer.counter.ScheduledCounter;

class TestCounter {
	
	Timer dummyTimer = mock(Timer.class);
	
	ScheduledCounter counter;
	
	@BeforeEach
	void setup() {
		// Fast counter works by using 0.1 * second as a measurement unit.
		// So instead of 1000 miliseconds being one second, its 100 miliseconds.
		// 10 times faster. So we use Thread.sleep(150).
		counter = new FastCounter();
		counter.setTimer(dummyTimer);
	}
	
	@Test
	void shouldCountUp() throws InterruptedException {
		counter.countUp();
		Thread.sleep(150);
		
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
		Thread.sleep(150);
		
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
