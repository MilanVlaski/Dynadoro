package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.TestTimer.REST_DURATION;
import static test.TestTimer.WORK_DURATION;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import display.Display;
import recording.History2;
import test.helpers.Moment;
import timer.Timer;
import timer.counter.Counter;

public class TestTimerPausing
{
	@Mock
	Display dummyDisplay;
	@Mock
	Counter dummyCounter;
	@Mock
	History2 dummyHistory;

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
	void timeShouldStop_WhenPausing_WhileWorking()
	{
		timer.begin(moment.current());

		int timeAtPause = 3;
		timer.pause(moment.afterSeconds(timeAtPause));

		assertEquals(timeAtPause, timer.seconds(moment.current()));
		assertEquals(timeAtPause, timer.seconds(moment.afterSeconds(123)));
	}

	@Test
	void timeShouldStopWhenPausing_WhileResting()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		int timeSpentResting = 1;
		timer.pause(moment.afterSeconds(timeSpentResting));

		assertEquals(REST_DURATION - timeSpentResting, timer.seconds(moment.current()));
		assertEquals(REST_DURATION - timeSpentResting,
		        timer.seconds(moment.afterSeconds(timeSpentResting)));
	}

	@Test
	void shouldResumeWork_WhereLeftOff_AfterPausing()
	{
		timer.begin(moment.current());

		int timeAtPause = 3;
		timer.pause(moment.afterSeconds(timeAtPause));
		timer.resume(moment.afterSeconds(123));

		assertEquals(timeAtPause, timer.seconds(moment.current()));
		assertEquals(timeAtPause + 1, timer.seconds(moment.afterSeconds(1)));
	}

	@Test
	void shouldResumeRest_WhereLeftOff_AfterPausing()
	{
		int timeSpentResting = 3;

		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		timer.pause(moment.afterSeconds(timeSpentResting));
		timer.resume(moment.afterSeconds(123));

		assertEquals(REST_DURATION - timeSpentResting,
		        timer.seconds(moment.current()));
		assertEquals(REST_DURATION - timeSpentResting - 1,
		        timer.seconds(moment.afterSeconds(1)));
	}

	@Test
	void shouldRest_AfterPausingWork()
	{
		timer.begin(moment.current());
		timer.pause(moment.afterSeconds(WORK_DURATION));
		timer.rest(moment.afterSeconds(123));

		assertEquals(REST_DURATION, timer.seconds(moment.current()));
		assertEquals(REST_DURATION - 1, timer.seconds(moment.afterSeconds(1)));
	}

	@Test
	void shouldGoToWork_WhilePausingRest()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));
		timer.pause(moment.afterSeconds(1));
		timer.begin(moment.afterSeconds(123));

		assertEquals(0, timer.seconds(moment.current()));
		assertEquals(1, timer.seconds(moment.afterSeconds(1)));
	}

}
