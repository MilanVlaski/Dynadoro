package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

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

public class TestRedesignedTimer
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
	void timeShouldBeZero_IfNotStarted()
	{ assertEquals(0, timer.seconds(TIME)); }

	@Test
	void timeShouldMoveForward_AfterOneSecond()
	{
		timer.begin(moment.current());

		assertEquals(0, timer.seconds(moment.current()));
		assertEquals(1, timer.seconds(moment.afterSeconds(1)));
	}

	public static final int WORK_DURATION = 25;
	public static final int WORK_REST_RATIO = 5 / 1;
	public static final int REST_DURATION = WORK_DURATION / WORK_REST_RATIO;

	@Test
	void restShouldBeCorrectlyShorterThanWork()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		assertEquals(WORK_DURATION / WORK_REST_RATIO, timer.seconds(moment.current()));
	}

	@Test
	void shouldCountDown_WhileTakingRest1()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		assertEquals(REST_DURATION - 1, timer.seconds(moment.afterSeconds(1)));
	}

	@Test
	void shouldCountDown_WhileTakingRest2()
	{
		LocalDateTime now = TIME;
		LocalDateTime afterWork = now.plusSeconds(WORK_DURATION);
		LocalDateTime oneSecondAfterWork = afterWork.plusSeconds(1);

		timer.begin(now);
		timer.rest(afterWork);
		assertEquals(REST_DURATION - 1, timer.seconds(oneSecondAfterWork));
	}

	@Test
	void shouldStopCounting_AfterRestIsOver()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		assertEquals(0, timer.seconds(moment.afterSeconds(REST_DURATION + 100)));
	}

	@Test
	void shouldResetTime_AfterGoingBackToWork_FromBreak()
	{
		int greaterThanMinimumBreakSeconds = 123;

		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(greaterThanMinimumBreakSeconds));
		timer.begin(moment.current());

		assertEquals(0, timer.seconds(moment.current()));
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
