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
 * This class tests the Timer internals related to checking time. I used
 * TestDisplay to test that Counter and Display are exhibiting the appropriate
 * behavior. This is done so that the simple, time related behavior stays here,
 * and updating the display and starting a dummyCounter (which mostly just
 * updated the display) is separate. There is some overlap in test setup between
 * these, but it shouldn't be major.
 * 
 * @author Milan Vlaski
 *
 */
public class TestTimer
{

	public static class Moment
	{
		// number is randomly picked, because currentTime() returns a random number
		private int currentTime = 36;

		public Moment()
		{}

		public Moment(int currentTime)
		{ this.currentTime = currentTime; }

		public int after(int seconds)
		{ return currentTime += seconds; }

		public int current()
		{ return currentTime; }
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
	void setup()
	{
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
	public static final int WORK_REST_RATIO = 5 / 1;
	public static final int REST_DURATION = TWENTY_FIVE / WORK_REST_RATIO;

	@Test
	void restShouldTakeFiveTimesShorterThanWork() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.current());

		timer.begin();
		timer.rest();
		assertEquals(REST_DURATION, timer.displayedTime());
	}

	@Test
	void shouldStopCountingAfterRestIsOver() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE),
						moment.after(999));

		timer.begin();
		timer.rest();

		assertEquals(0, timer.displayedTime());
	}

	@Test
	void shouldResetTimeAfterGoingBackToWork()
	{
		timer.begin();
		timer.rest();
		timer.begin();
		assertEquals(0, timer.displayedTime());
	}

	@Test
	void shouldThrowException_IfTriesToStartTwice()
	{
		timer.begin();
		assertThrows(IllegalOperationException.class, () -> timer.begin());
	}

	@Test
	void shouldThrowException_IfTakesRestWithoutWorking()
	{ assertThrows(IllegalOperationException.class, () -> timer.rest()); }

	@Test
	void shouldThrowException_IfTriesToTakeRestWhileResting()
	{
		timer.begin();
		timer.rest();
		assertThrows(IllegalOperationException.class, () -> timer.rest());
	}

	@Test
	void shouldThrowException_IfPausingNothing()
	{ assertThrows(IllegalOperationException.class, () -> timer.pause()); }

	@Test
	void timeShouldStopWhenPausing_WhileWorking() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(5), moment.after(1));

		timer.begin();
		timer.pause();

		assertEquals(5, timer.displayedTime());
	}

	@Test
	void shouldCountDown_WhileTakingRest() {
		when(mockClock.currentTimeSeconds())
		.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(1));
		
		timer.begin();
		timer.rest();
		assertEquals(REST_DURATION - 1, timer.displayedTime());
	}

	@Test
	void timeShouldStopWhenPausing_WhileResting() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(1),
						moment.current());

		timer.begin();
		timer.rest();
		timer.pause();
		
		assertEquals(REST_DURATION - 1, timer.displayedTime());
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
	void shouldResumeRest() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(1),
						moment.current(), moment.current(), moment.after(1));

		timer.begin();

		timer.rest(); // time = 5

		timer.pause(); // time = 4

		timer.resume();
		assertEquals(4, timer.displayedTime());

		assertEquals(3, timer.displayedTime());
	}

	@Test
	void shouldResumeRestTwice() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(1),
						moment.current(), moment.after(1), moment.after(1), moment.after(1), moment.after(1));

		timer.begin();
		timer.rest();
		timer.pause();
		timer.resume();

		timer.pause();

		timer.resume();
		assertEquals(2, timer.displayedTime());

		assertEquals(1, timer.displayedTime());
	}

	@Test
	void shouldThrow_IfTriesToResumeInIdleState()
	{ assertThrows(IllegalOperationException.class, () -> timer.resume()); }

	@Test
	void shouldRest_AfterPausingWork() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE), moment.after(123),
						moment.after(1));

		timer.begin();
		timer.pause();
		timer.rest();

		assertEquals(4, timer.displayedTime());
	}

	// TODO make it pass
//	@Test
//	void shouldThrowIfRests_WhilePausingRest() {
//		timer.begin();
//		timer.rest();
//		timer.pause();
//		
//		assertThrows(IllegalOperationexception.class, () -> timer.rest());
//	}

	@Test
	void shouldResetTimer() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(moment.current(), moment.after(5), moment.after(1));
		
		timer.begin();
		timer.reset();
		
		assertEquals(0, timer.displayedTime());
	}

}
