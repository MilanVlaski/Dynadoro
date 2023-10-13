package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import display.Display;
import timer.Clock;
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
		assertEquals(0, idle.displayedTime(123));
	}

	@Test
	void breakShouldNotBeAllowed() {
		Idle idle = new Idle(dummyTimer);
		assertThrows(IllegalOperation.class, () -> idle.takeBreak(0));
	}

	@Test
	void shouldMeasureElapsedTime() {
		Working working = new Working(dummyTimer, 2);
		assertEquals(5 - 2, working.displayedTime(5));
	}

	@Test
	void shouldThrowExceptionIfTriesToStartTimerAgain() {
		Working working = new Working(dummyTimer, 0);
		assertThrows(IllegalOperation.class, () -> working.begin(0));
	}

	@Test
	void shouldShowBreakTime() {
		TakingBreak takingBreak = new TakingBreak(dummyTimer, 25, 25);
		assertEquals(25 / 5, takingBreak.displayedTime(25));
	}

	@Test
	void shouldShowTimePassingBackwards() {
		TakingBreak takingBreak = new TakingBreak(dummyTimer, 25, 25);
		assertEquals(5 - 2, takingBreak.displayedTime(25 + 2));
	}

	@Test
	void shouldShowTimeNotGoingPastZero() {
		TakingBreak takingBreak = new TakingBreak(dummyTimer, 25, 25);
		assertEquals(0, takingBreak.displayedTime(25 + 999));
	}
	
	@Test
	void shouldThrowExceptionIfAlreadyOnBreak() {
		TakingBreak takingBreak = new TakingBreak(dummyTimer, 0, 0);
		assertThrows(IllegalOperation.class, () -> takingBreak.takeBreak(0));
	}
}
