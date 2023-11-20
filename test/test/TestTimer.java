package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import test.helpers.Moment;
import timer.Timer;
import timer.counter.Counter;

public class TestTimer
{

	@Mock
	Display dummyDisplay;
	@Mock
	Counter dummyCounter;

	@InjectMocks
	Timer timer;

	//
	public static final LocalDateTime TIME = LocalDateTime.of(2023, 11, 13, 16, 37);
	private Moment moment;

	@BeforeEach
	void setup()
	{
		MockitoAnnotations.openMocks(this);
		moment = new Moment(TIME);
	}

	@Test
	void timeShouldBeZero_IfNotStarted()
	{ assertEquals(0, timer.seconds(TIME)); }

	@Test
	void timeShouldMoveForwardFromZero_AfterOneSecond()
	{
		timer.begin(moment.current());

		assertEquals(0, timer.seconds(moment.current()));
		assertEquals(1, timer.seconds(moment.afterSeconds(1)));
	}

	public static final int WORK_DURATION = 25;
	public static final int WORK_REST_RATIO = 5 / 1;
	public static final int REST_DURATION = WORK_DURATION / WORK_REST_RATIO;

	@Test
	void shouldCountDown_FromCorrectRestDuration()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		assertEquals(WORK_DURATION / WORK_REST_RATIO, timer.seconds(moment.current()));
		assertEquals(WORK_DURATION / WORK_REST_RATIO - 1,
		        timer.seconds(moment.afterSeconds(1)));
	}

	@Test
	void shouldStopCounting_AfterRestIsOver()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		assertEquals(0, timer.seconds(moment.afterSeconds(REST_DURATION + 123)));
	}

	@Test
	void shouldStartTimerFromZero_AfterGoingBackToWork_FromBreak()
	{
		int greaterThanMinimumBreakSeconds = 123;

		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(greaterThanMinimumBreakSeconds));
		timer.begin(moment.current());

		assertEquals(0, timer.seconds(moment.current()));
		assertEquals(1, timer.seconds(moment.afterSeconds(1)));
	}

	@Test
	void shouldSetTimeToZero_AfterReset()
	{
		timer.begin(moment.current());
		timer.reset(moment.afterSeconds(3));

		assertEquals(0, timer.seconds(moment.current()));
		assertEquals(0, timer.seconds(moment.afterSeconds(1)));
	}
	
}
