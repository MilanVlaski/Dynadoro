package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import display.Display;
import record.UsageRecord;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;
import timer.state.TimerState.IllegalOperationException;

/**
 * This class tests the Timer internals related to checking time. I used
 * TestDisplay to test that Counter and Display are exhibiting the appropriate
 * behavior. This is done so that the simple, time related behavior stays here,
 * and updating the display and starting a counter (which mostly just updated
 * the display) is separate. There is some overlap in test setup between these,
 * but it shouldn't be major.
 * 
 * @author Milan Vlaski
 *
 */
public class TestTimer {

	public static class Moment {
		// number is random, serves currentTime() returns a random number
		private int currentTime = 36;

		private int after(int seconds) {
			return currentTime += seconds;
		}

		private int current() {
			return currentTime;
		}
	}

	@Mock
	Clock mockClock;
	@Mock
	Display dummyDisplay;
	@Mock
	Counter dummyCounter;

	@InjectMocks
	Timer timer;

	Moment moment;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		moment = new Moment();
	}

	@Test
	void timeShouldBeZeroIfNotStarted() {
		when(mockClock.currentTimeSeconds()).thenReturn(999);
		
		assertEquals(0, timer.displayedTime());
	}

	@Test
	void shouldMeasureElapsedTime() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(1), moment.after(1));

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
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.current());

		timer.begin();
		timer.takeBreak();
		assertEquals(BREAK_DURATION, timer.displayedTime());
	}

	@Test
	void shouldStopCountingAfterBreakIsOver() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE),
						moment.after(999));

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
				.thenReturn(moment.current(), moment.after(5), moment.after(1));

		timer.begin();
		timer.pause();

		assertEquals(5, timer.displayedTime());
	}

	@Test
	void shouldCountDown_WhileTakingBreak() {
		when(mockClock.currentTimeSeconds())
		.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(1));
		
		timer.begin();
		timer.takeBreak();
		assertEquals(BREAK_DURATION - 1, timer.displayedTime());
	}

	@Test
	void timeShouldStopWhenPausing_WhileOnBreak() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(1),
						moment.current());

		timer.begin();
		timer.takeBreak();
		timer.pause();
		
		assertEquals(BREAK_DURATION - 1, timer.displayedTime());
	}

	@Test
	void shouldResumeWork() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(5), moment.after(1), moment.current(), moment.after(1));

		timer.begin();

		timer.pause(); 

		timer.resume(); 
		assertEquals(5, timer.displayedTime());
		
		assertEquals(6, timer.displayedTime());
	}

	@Test
	void shouldPauseAndResumeWorkTwice() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(5), moment.after(1),
						moment.after(1), moment.after(1), moment.current(), moment.after(1));

		timer.begin();
		timer.pause(); 
		timer.resume();

		timer.pause(); 

		timer.resume();
		assertEquals(6, timer.displayedTime());

		assertEquals(7, timer.displayedTime());
	}

	@Test
	void shouldResumeBreak() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(1),
						moment.current(), moment.current(), moment.after(1));

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
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(1),
						moment.current(), moment.after(1), moment.after(1), moment.after(1), moment.after(1));

		timer.begin();
		timer.takeBreak();
		timer.pause();
		timer.resume();

		timer.pause();

		timer.resume();
		assertEquals(2, timer.displayedTime());

		assertEquals(1, timer.displayedTime());
	}

	@Test
	void shouldThrow_IfTriesToResumeInIdleState() {
		assertThrows(IllegalOperationException.class, () -> timer.resume());
	}

	@Test
	void shouldGoToBreak_AfterPausingWork() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(123),
						moment.after(1));

		timer.begin();
		timer.pause();
		timer.takeBreak();

		assertEquals(4, timer.displayedTime());
	}

	// TODO make it pass
//	@Test
//	void shouldThrowIfTakesBreak_WhilePausingBreak() {
//		timer.begin();
//		timer.takeBreak();
//		timer.pause();
//		
//		assertThrows(IllegalOperationrxception.class, () -> timer.takeBreak());
//	}

	@Test
	void shouldResetTimer() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(moment.current(), moment.after(5), moment.after(1));
		
		timer.begin();
		timer.reset();
		
		assertEquals(0, timer.displayedTime());
	}

	@Test
	void shouldStartRecording() {
		UsageRecord record = new UsageRecord();
		timer.startRecording(record);
		assertEquals("2023-10-23, Monday, Idle, 09:00, -'\n", record.toString());
	}
	
}
