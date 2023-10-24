package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;
import timer.counter.ScheduledCounter;

class TestTimer {

	@Mock
	Clock mockClock;
	@Mock
	Display dummyDisplay;
	@Mock
	Counter mockCounter;

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
	void shouldMeasureElapsedTime() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0, 1, 2);
		
		timer.begin();
		// 1 second passes...
		assertEquals(1, timer.displayedTime());
		// 2 seeconds pass...
		assertEquals(2, timer.displayedTime());
	}

	static final int TWENTY_FIVE = 25;
	static final int WORK_BREAK_RATIO = 5;
	static final int BREAK_DURATION = TWENTY_FIVE / WORK_BREAK_RATIO;

	@Test
	void breakShouldTakeFiveTimesShorterThanWork() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0, TWENTY_FIVE, TWENTY_FIVE);
		
		timer.begin();
		timer.takeBreak();
		assertEquals(BREAK_DURATION, timer.displayedTime());
	}

	@Test
	void shouldCountDown_WhileTakingBreak() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0, TWENTY_FIVE, TWENTY_FIVE + 1);

		timer.begin();
		timer.takeBreak();
		assertEquals(BREAK_DURATION - 1, timer.displayedTime());
	}

	@Test
	void shouldStopCountingAfterBreakIsOver() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0, TWENTY_FIVE, 999);
	
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

	@Test
	void shouldStartCounting_WhenStartTimer() {
		timer.begin();
		verify(mockCounter).countUp();
	}

	@Test
	void shouldBeRunningOnBreak_ForCorrectDuration() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0, TWENTY_FIVE);
		
		timer.begin();
		timer.takeBreak();
		
		verify(mockCounter).count(BREAK_DURATION);
	}

}
