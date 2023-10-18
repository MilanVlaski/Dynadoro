package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static test.TestTimer.WORK_BREAK_RATIO;

import org.junit.jupiter.api.Test;

import display.Display;
import timer.Clock;
import timer.Counter;
import timer.Timer;
import timer.state.Idle;
import timer.state.TakingBreak;
import timer.state.TimerState.IllegalOperation;
import timer.state.Working;

public class TestTimerState {

	Display dummyDisplay = mock(Display.class);
	Clock dummyClock = mock(Clock.class);

	Timer dummyTimer = new Timer(dummyClock, dummyDisplay);

	@Test
	void timeShouldAlwaysBeZero() {
		Idle idle = new Idle(dummyTimer);
		assertEquals(0, idle.displayedTime(999));
	}

	@Test
	void breakShouldNotBeAllowed() {
		Idle idle = new Idle(dummyTimer);
		assertThrows(IllegalOperation.class, () -> idle.takeBreak(0));
	}

	@Test
	void shouldMeasureElapsedTime() {
		int now = 2;
		int threeSecondsLater = 5;
		Working working = new Working(dummyTimer, now);
		assertEquals(3, working.displayedTime(threeSecondsLater));
	}

	@Test
	void shouldThrowExceptionIfTriesToStartTimerAgain() {
		Working working = new Working(dummyTimer, 0);
		assertThrows(IllegalOperation.class, () -> working.begin(0));
	}

	static final int WORK_DURATION = TestTimer.TWENTY_FIVE;
	
	@Test
	void shouldShowBreakTime() {
		int now = 66;
		TakingBreak takingBreak = new TakingBreak(dummyTimer, now, WORK_DURATION);
		assertEquals(WORK_DURATION / WORK_BREAK_RATIO, takingBreak.displayedTime(now));
	}

	@Test
	void shouldShowTimePassingBackwards() {
		int now = 66;
		TakingBreak takingBreak = new TakingBreak(dummyTimer, now, WORK_DURATION);
		assertEquals(WORK_DURATION / WORK_BREAK_RATIO - 2, takingBreak.displayedTime(now + 2));
	}

	@Test
	void shouldShowTimeNotGoingPastZero() {
		int now = 123;
		TakingBreak takingBreak = new TakingBreak(dummyTimer, now, now);
		assertEquals(0, takingBreak.displayedTime(now + 999));
	}

	@Test
	void shouldThrowExceptionIfAlreadyOnBreak() {
		TakingBreak takingBreak = new TakingBreak(dummyTimer, 0, 0);
		assertThrows(IllegalOperation.class, () -> takingBreak.takeBreak(0));
	}
}
