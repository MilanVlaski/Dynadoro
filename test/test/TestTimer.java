package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import display.Display;
import timer.Clock;
import timer.Timer;

class TestTimer {

	Clock mockClock = mock(Clock.class);
	Display display = mock(Display.class);

	Timer timer = new Timer(mockClock, display);

	@Test
	void timeShouldBeZeroIfNotStarted() {
		when(mockClock.currentTimeSeconds()).thenReturn(999);
		
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
		assertEquals(5-0, timer.time());
	}

	@Test
	void shouldBeginWork() {
		timer.begin();
		assertTrue(timer.working());
	}

	@Test
	void breakShouldTakeFiveTimesShorterThanWork() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(5);
		
		timer.begin();
		// 5 seconds pass...
		timer.takeBreak();
		assertEquals(1, timer.time());
//		assertFalse(timer.working());
	}
}
