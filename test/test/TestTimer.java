package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import timer.Clock;
import timer.Timer;

class TestTimer {

	@Mock
	Clock mockClock = mock(Clock.class);
	
	Timer timer = new Timer(mockClock);
	
	@Test
	void timeShouldBeZeroIfNotStarted() {
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
		assertEquals(5, timer.time());
	}
	
	@Test
	void shouldBeginWork(){
		timer.begin();
		assertTrue(timer.working());
	}
}
