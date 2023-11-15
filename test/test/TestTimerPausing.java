package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static test.TestRedesignedTimer.REST_DURATION;
import static test.TestRedesignedTimer.WORK_DURATION;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;

public class TestTimerPausing
{
	@Mock
	Clock dummyClock;
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
	void timeShouldStop_WhenPausing_WhileWorking()
	{
		timer.begin(moment.current());
		timer.pause(moment.afterSeconds(3));

		assertEquals(3, timer.seconds(moment.afterSeconds(123)));
	}

	@Test
	void timeShouldStopWhenPausing_WhileResting()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));
		timer.pause(moment.afterSeconds(1));

		assertEquals(REST_DURATION - 1, timer.seconds(moment.afterSeconds(1)));
	}

	@Test
	void shouldResumeWork_WhereLeftOff_AfterPausing()
	{
		int timeAtPause = 3;

		timer.begin(moment.current());
		timer.pause(moment.afterSeconds(timeAtPause));
		timer.resume(moment.afterSeconds(123));

		assertEquals(timeAtPause, timer.seconds(moment.current()));
	}

	@Test
	void shouldResumeRest_WhereLeftOff_AfterPausing()
	{
		int timeAfterBreakBeforePausing = 3;

		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		timer.pause(moment.afterSeconds(timeAfterBreakBeforePausing));
		timer.resume(moment.afterSeconds(123));

		assertEquals(REST_DURATION - timeAfterBreakBeforePausing,
		        timer.seconds(moment.current()));
	}

	@Test
	void shouldRest_AfterPausingWork() {
		timer.begin(moment.current());
		timer.pause(moment.afterSeconds(WORK_DURATION));
		timer.rest(moment.afterSeconds(123));

		assertEquals(REST_DURATION, timer.seconds(moment.current()));
	}

}
