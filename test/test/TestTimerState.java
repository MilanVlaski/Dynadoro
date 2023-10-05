package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import timer.state.Idle;
import timer.state.IllegalOperation;
import timer.state.TakingBreak;
import timer.state.Working;

public class TestTimerState {

	@Test
	void timeShouldAlwaysBeZero() {
		Idle idle = new Idle(null);
		assertEquals(0, idle.displayedTime(123));
	}

	@Test
	void breakShouldNotBeAllowed() {
		Idle idle = new Idle(null);
		assertThrows(IllegalOperation.class, () -> idle.takeBreak(0));
	}

	@Test
	void shouldMeasureElapsedTime() {
		Working working = new Working(null, 2);
		assertEquals(4 - 2, working.displayedTime(4));
	}

	@Test
	void shouldThrowExceptionIfTriesToStartTimerAgain() {
		Working working = new Working(null, 0);
		assertThrows(IllegalOperation.class, () -> working.begin(0));
	}

	@Test
	void shouldShowBreakTime() {
		TakingBreak takingBreak = new TakingBreak(null, 25, 25);
		assertEquals(25 / 5, takingBreak.displayedTime(25));
	}

	@Test
	void shouldShowTimePassingBackwards() {
		TakingBreak takingBreak = new TakingBreak(null, 25, 25);
		assertEquals(5 - 2, takingBreak.displayedTime(25 + 2));
	}

	@Test
	void shouldShowTimeNotGoingPastZero() {
		TakingBreak takingBreak = new TakingBreak(null, 25, 25);
		assertEquals(0, takingBreak.displayedTime(25 + 999));
	}
	
	@Test
	void shouldThrowExceptionIfAlreadyOnBreak() {
		TakingBreak takingBreak = new TakingBreak(null, 0, 0);
		assertThrows(IllegalOperation.class, () -> takingBreak.takeBreak(0));
	}
}
