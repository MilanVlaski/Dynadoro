package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timer.Clock;
import timer.Timer;
import timer.state.Idle;
import timer.state.IllegalOperation;
import timer.state.Working;

class TestTimer {

	Timer timer;
	Clock mockClock = mock(Clock.class);

	@BeforeEach
	void setup() {
		timer = new Timer(mockClock);
	}

	@Test
	void timeShouldBeZeroIfNotStarted() {
		when(mockClock.currentTimeSeconds()).thenReturn(1);
		
		assertEquals(0, timer.time());
	}

	@Test
	void shouldMeasureElapsedTime() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(1)
						.thenReturn(2)
						.thenReturn(3);
		
		timer.begin();
		// 1 second passes...
		assertEquals(1, timer.time());
		// 1 second passes...
		assertEquals(2, timer.time());
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

	// what happens if i take a break after 3 seconds? do i go straight back to
	// work?

	@Test
	void shouldStopCountingAfterBreakIsOver() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(25)
						.thenReturn(66);
	
		timer.begin();
		timer.takeBreak();
		assertEquals(0, timer.time());
	}
}
