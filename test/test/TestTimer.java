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
	}
	
	@Test
	void shouldMeasureElapsedTime() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(25)
						.thenReturn(26);
		
		timer.begin();
		// 25 seconds pass...
		assertEquals(25-0, timer.time());
		// 1 second passes
		assertEquals(26, timer.time());
	}

	@Test
	void breakShouldTakeFiveTimesShorterThanWork() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(25);
		
		timer.begin();
		// 25 seconds pass...
		timer.takeBreak();
		assertEquals(5, timer.time());
	}
	
	@Test
	void shouldCountDown_WhileTakingBreak() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(25)
						.thenReturn(25)
						.thenReturn(26);
		
		timer.begin();
		// 25 seconds pass...
		timer.takeBreak();
		assertEquals(5, timer.time());
		// 1 second passes...
		assertEquals(4, timer.time());
	}
}
