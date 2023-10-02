package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import timer.Clock;
import timer.Timer;

class TestTimer {

	@Mock
	Clock mockClock = mock(Clock.class);

	@Test
	void timeShouldBeZeroIfNotStarted() {
		Timer timer = new Timer(mockClock);
		assertEquals(0, timer.time());
	}

	@Test
	void shouldMeasureElapsedTime() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(0)
				.thenReturn(5);
		
		Timer timer = new Timer(mockClock);
		
		timer.begin();
		assertEquals(5, timer.time());
	}
}
