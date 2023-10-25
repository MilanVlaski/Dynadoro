package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static test.TestTimer.WORK_BREAK_RATIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;
import timer.state.Idle;
import timer.state.TakingBreak;
import timer.state.TimerState.IllegalOperation;
import timer.state.Working;
/**
 * These tests may be superflous, because the behavior of each state is invoked
 * when testing the behavior of Timer class. Having states is implementation
 * detail that can be left untested. However, since states are kind of crucial
 * to my understanding of the problem, these tests help to illustrate that.
 * @author Milan Vlaski
 *
 */
public class TestTimerState {

	@Mock
	Display dummyDisplay = mock(Display.class);
	@Mock
	Clock dummyClock = mock(Clock.class);
	@Mock
	Counter dummyCounter = mock(Counter.class);

	@InjectMocks
	Timer dummyTimer;

	@BeforeEach
	void injectMocks() {
		MockitoAnnotations.openMocks(this);
	}

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
		Working working = new Working(dummyTimer, now, 0);
		assertEquals(3, working.displayedTime(threeSecondsLater));
	}

	@Test
	void shouldThrowExceptionIfTriesToStartTimerWhileItIsRunning() {
		Working working = new Working(dummyTimer, 0, 0);
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
	void shouldShowTimePassingBackwards_OnBreak() {
		int now = 66;
		TakingBreak takingBreak = new TakingBreak(dummyTimer, now, WORK_DURATION);
		assertEquals(WORK_DURATION / WORK_BREAK_RATIO - 2, takingBreak.displayedTime(now + 2));
	}

	@Test
	void shouldShowTimeNotGoingPastZero_AfterBreak() {
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
