package test.display;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static test.TestTimer.REST_DURATION;
import static test.TestTimer.WORK_DURATION;

import java.time.Clock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import display.Display.DisplayState;
import test.TestTimer;
import test.helpers.Moment;
import timer.Timer;
import timer.counter.Counter;

public class TestDisplay
{

	@Mock
	Clock dummyClock;
	@Mock
	Display mockDisplay;
	@Mock
	Counter mockCounter;

	@InjectMocks
	Timer timer;

	Moment moment;

	@BeforeEach
	void injectMocks()
	{
		MockitoAnnotations.openMocks(this);
		moment = new Moment(TestTimer.TIME);
	}

	@Test
	void shouldShowIdle_OnInit()
	{ verify(mockDisplay).show(0, DisplayState.IDLE); }

	@Test
	void shouldShowWorkingState_WhenStarting()
	{
		timer.begin(moment.current());

		verify(mockDisplay).show(0, DisplayState.WORKING);
		verify(mockCounter).countUp();
	}

	@Test
	void shouldShowAppropriateTime_AndState_WhenResting()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		verify(mockDisplay).show(REST_DURATION, DisplayState.RESTING);
		verify(mockCounter).count(REST_DURATION);
	}

	@Test
	void shouldShowPause_DuringWork()
	{
		timer.begin(moment.current());
		timer.pause(moment.afterSeconds(3));

		verify(mockDisplay).show(3, DisplayState.WORK_PAUSE);
		verify(mockCounter, times(2)).stop();
	}

	@Test
	void shouldShowPause_DuringRest()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));
		timer.pause(moment.afterSeconds(2));

		verify(mockDisplay).show(REST_DURATION - 2, DisplayState.REST_PAUSE);
		verify(mockCounter, times(2)).stop();
	}

	@Test
	void shouldShowResumingWork()
	{
		timer.begin(moment.current());
		timer.pause(moment.afterSeconds(1));
		timer.resume(moment.afterSeconds(123));

		verify(mockDisplay).show(1, DisplayState.WORKING);
		verify(mockCounter, times(2)).countUp();
	}

	@Test
	void shouldShowResumingRest()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));
		timer.pause(moment.afterSeconds(1));
		timer.resume(moment.afterSeconds(123));

		verify(mockDisplay).show(REST_DURATION - 1, DisplayState.REST_PAUSE);
		verify(mockCounter).count(REST_DURATION - 1);
	}

	@Test
	void shouldStopCounter_WhenResetting()
	{
		timer.begin(moment.current());
		timer.reset(moment.afterSeconds(123));

		verify(mockCounter, atMost(2)).stop();
	}
}
