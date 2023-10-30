package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import timer.state.TimerState.IllegalOperationException;
/**
 * This class tests the Timer internals related to checking time.
 * I used TestDisplay to test that Counter and Display are exhibiting the 
 * appropriate behavior. This is done so that the simple, time related behavior
 * stays here, and updating the display and starting a counter (which mostly
 * just updated the display) is separate. There is some overlap in test setup
 * between these, but it shouldn't be major.
 * @author Milan Vlaski
 *
 */
public class TestTimer {

	@Mock
	Clock mockClock;
	@Mock
	Display dummyDisplay;
	@Mock
	Counter dummyCounter;

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

	public static final int TWENTY_FIVE = 25;
	public static final int WORK_BREAK_RATIO = 5;
	public static final int BREAK_DURATION = TWENTY_FIVE / WORK_BREAK_RATIO;

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
	void shouldThrowException_IfTriesToStartTwice() {
		timer.begin();
		assertThrows(IllegalOperationException.class, () -> timer.begin());
	}

	@Test
	void shouldThrowException_IfTakesBreakWithoutWorking() {
		assertThrows(IllegalOperationException.class, () -> timer.takeBreak());
	}

	@Test
	void shouldThrowException_IfTriesToTakeBreakWhileOnBreak() {
		timer.begin();
		timer.takeBreak();
		assertThrows(IllegalOperationException.class, () -> timer.takeBreak());
	}

	@Test
	void shouldThrowException_IfPausingNothing() {
		assertThrows(IllegalOperationException.class, () -> timer.pause());
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
		assertEquals(BREAK_DURATION - 1, timer.displayedTime());
	}

	// I use hundreds here for time because when you start from 0, you get uncaught
	// errors. The reason is that at runtime clock always returns time > 0,
	// so if we were to use 0, that itself is not a problem, but it doesn't
	// force us to actually compute the time properly, because using adding or
	// subtracting with 0 does nothing (untested behavior).
	@Test
	void shouldResumeWork() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(100, 105, 106, 106, 107);
		
		timer.begin(); // 0
		
		timer.pause(); // 5
		
		timer.resume(); // one second later...
		assertEquals(5, timer.displayedTime());
		// one second later...
		assertEquals(6, timer.displayedTime());
	}

	@Test
	void shouldPauseAndResumeWorkTwice() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(100, 105, 106, 107, 108, 108, 109);
		
		timer.begin(); // time = 0
		timer.pause(); // time = 5
		timer.resume();
		
		timer.pause(); // time = 6
		
		timer.resume();
		assertEquals(6, timer.displayedTime());
		
		assertEquals(7, timer.displayedTime());
	}

	@Test
	void shouldResumeBreak() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(100, 125, 126, 126, 126, 127);
		
		timer.begin();

		timer.takeBreak(); // time = 5

		timer.pause(); // time = 4

		timer.resume();
		assertEquals(4, timer.displayedTime());

		assertEquals(3, timer.displayedTime());
	}

	@Test
	void shouldResumeBreakTwice() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(100, 125, 126, 126, 127, 128, 129, 130);
		
		timer.begin();
		timer.takeBreak(); // time = 5
		timer.pause(); // time = 4
		timer.resume();

		
		timer.pause();  // time = 3

		timer.resume();
		assertEquals(2, timer.displayedTime());

		assertEquals(1, timer.displayedTime());
	}
	
	@Test
	void shouldThrow_IfTriesToResumeInIdleState() {
		assertThrows(IllegalOperationException.class, () -> timer.resume());
	}
}
