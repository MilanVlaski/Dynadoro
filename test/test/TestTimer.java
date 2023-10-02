package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import display.Display;
import timer.Clock;
import timer.Timer;

class TestTimer {

	Clock mockClock = mock(Clock.class);
	Display display = mock(Display.class);

	Timer timer = new Timer(mockClock, display);

	@Test
	void timeShouldBeZeroIfNotStarted() {
		when(mockClock.currentTimeSeconds()).thenReturn(123);
		
		assertEquals(0, timer.time());
		assertFalse(timer.working());
	}

	@Test
	void shouldMeasureElapsedTime() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(5);
		
		timer.begin();
		// 5 seconds pass...
		assertEquals(5 - 0, timer.time());
	}

	@Test
	void shouldBeginWork() {
		timer.begin();
		assertTrue(timer.working());
	}

	@Test
	void testName() {
		timer.begin();
	}
}
