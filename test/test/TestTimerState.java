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
		Working working = new Working(null, 5);
		assertEquals(30 - 5, working.displayedTime(30));
	}

	@Test
	void shouldThrowExceptionIfTriesToStartTimerAgain() {
		Working working = new Working(null, 0);
		assertThrows(IllegalOperation.class, () -> working.begin(0));
	}
	
	@Test
	void shouldShowBreakTime() {
		TakingBreak takingBreak = new TakingBreak(null, 25, 25);
		assertEquals(5, takingBreak.displayedTime(25));
	}
}
