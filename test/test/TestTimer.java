package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import timer.state.TimerState.IllegalOperation;

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

	@Test
	void shouldThrowException_IfTriesToStartTwice() {
		timer.begin();
		assertThrows(IllegalOperation.class, () -> timer.begin());
	}

	@Test
	void shouldThrowException_IfTakesBreakWithoutWorking() {
		assertThrows(IllegalOperation.class, () -> timer.takeBreak());
	}

	@Test
	void shouldThrowException_IfTriesToTakeBreakWhileOnBreak() {
		timer.begin();
		timer.takeBreak();
		assertThrows(IllegalOperation.class, () -> timer.takeBreak());
	}

	@Test
	void shouldThrowException_IfPausingNothing() {
		assertThrows(IllegalOperation.class, () -> timer.pause());
	}

	@Test
	void timeShouldStopWhenPausing_WhileWorking() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0, 5, 6);
		
		timer.begin();
		timer.pause();
		
		assertEquals(5, timer.displayedTime());
	}

	@Test
	void timeShouldStopWhenPausing_WhileOnBreak() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0, TWENTY_FIVE, TWENTY_FIVE + 1, TWENTY_FIVE + 1);
		
		timer.begin();
		
		timer.takeBreak();
		
		timer.pause();
		assertEquals(4, timer.displayedTime());
	}

	@Test
	void shouldResumeWork() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0, 5, 6, 6, 7);
		
		timer.begin();
		
		timer.pause();
		
		timer.resume();
		assertEquals(5, timer.displayedTime());
		
		assertEquals(6, timer.displayedTime());
	}
	
	@Test
	void shouldResumeWork2() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(100, 105, 106, 106, 107);
		
		timer.begin();
		
		timer.pause();
		
		timer.resume();
		assertEquals(5, timer.displayedTime());
		
		assertEquals(6, timer.displayedTime());
	}

	@Test
	void shouldResumeBreak() {
		int TWENTY_SIX = TWENTY_FIVE + 1;
		when(mockClock.currentTimeSeconds())
				.thenReturn(0, TWENTY_FIVE, TWENTY_SIX, TWENTY_SIX, TWENTY_SIX, TWENTY_SIX + 1);
		timer.begin();

		timer.takeBreak(); // time = 5

		timer.pause(); // time = 4

		timer.resume();
		assertEquals(4, timer.displayedTime());

		assertEquals(3, timer.displayedTime());
	}

}
