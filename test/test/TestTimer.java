package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import timer.Clock;
import timer.Timer;

class TestTimer {

	@Mock
	Clock mockClock = mock(Clock.class);
	@Mock
	Display dummyDisplay = mock(Display.class);

	@InjectMocks
	Timer timer;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void timeShouldBeZeroIfNotStarted() {
		when(mockClock.currentTimeSeconds()).thenReturn(999);
		
		assertEquals(0, timer.displayedTime());
	}
	
	@Test
	void shouldNotBeRunning() {
		assertFalse(timer.isRunning());
	}
	
	@Test
	void shouldBeRunningWhenStarted() {
		timer.begin();
		assertTrue(timer.isRunning());
	}
	
	@Test
	void shouldBeRunningOnBreak() {
		timer.begin();
		timer.takeBreak();
		assertTrue(timer.isRunning());
	}

	@Test
	void shouldMeasureElapsedTime() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(1)
						.thenReturn(2);
		
		timer.begin();
		// 1 second passes...
		assertEquals(1, timer.displayedTime());
		// 1 second passes...
		assertEquals(2, timer.displayedTime());
	}

	static final int TWENTY_FIVE = 25;
	static final int WORK_BREAK_RATIO = 5;
	static final int BREAK_DURATION = TWENTY_FIVE / WORK_BREAK_RATIO;

	@Test
	void breakShouldTakeFiveTimesShorterThanWork() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(TWENTY_FIVE)
						.thenReturn(TWENTY_FIVE);
		
		timer.begin();
		// 25 seconds pass...
		timer.takeBreak();
		assertEquals(BREAK_DURATION, timer.displayedTime());
	}

	@Test
	void shouldCountDown_WhileTakingBreak() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(0)
				.thenReturn(TWENTY_FIVE)
				.thenReturn(TWENTY_FIVE)
				.thenReturn(TWENTY_FIVE + 1);

		timer.begin();
		// 25 seconds pass...
		timer.takeBreak();
		assertEquals(BREAK_DURATION, timer.displayedTime());
		// 1 second passes...
		assertEquals(BREAK_DURATION - 1, timer.displayedTime());
	}

	// what happens if i take a break after 3 seconds? do i go straight back to
	// work?

	@Test
	void shouldStopCountingAfterBreakIsOver() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(TWENTY_FIVE)
						.thenReturn(66);
	
		timer.begin();
		timer.takeBreak();
		assertEquals(0, timer.displayedTime());
	}

	@Test
	void shouldResetTimeAfterGoingBackToWork() {
		timer.begin();
		timer.takeBreak();
		timer.begin();
		assertEquals(0, timer.displayedTime());
	}
}
