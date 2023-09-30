package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import timer.Clock;
import timer.Timer;

class TestTimer {

	@Test
	void shouldStartWithZero() {
		Timer timer = new Timer(null);
		assertEquals(0, timer.time());
	}

	@Test
	void shouldMeasureElapsedTime() {
		Clock mockClock = mock(Clock.class);
		int ELAPSED = 5;
		
		when(mockClock.currentTimeSeconds())
				.thenReturn(0)
				.thenReturn(ELAPSED);
		Timer timer = new Timer(mockClock);
		
		timer.begin();
		assertEquals(ELAPSED, timer.end());
	}
}
