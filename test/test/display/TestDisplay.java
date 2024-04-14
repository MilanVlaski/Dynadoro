package test.display;

import static org.mockito.Mockito.*;
import static test.TestTimer.REST_DURATION;
import static test.TestTimer.WORK_DURATION;

import java.time.Clock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import display.Display;
import recording.History2;
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
	@Mock
	History2 dummyHistory;

	@InjectMocks
	Timer timer;

	Moment moment;

	@BeforeEach
	void injectMocks()
	{
		MockitoAnnotations.openMocks(this);
		moment = new Moment(TestTimer.ANY_DATETIME);
	}

	@Test
	void shouldShowIdle_OnInit()
	{
		verify(mockDisplay).show(0);
		verify(mockDisplay).showIdle();
	}

	@Test
	void shouldShowWorkingState_WhenStarting()
	{
		timer.begin(moment.current());

		verify(mockDisplay).showWorking();
		verify(mockCounter).countUp();
	}

	@Test
	void shouldShowAppropriateTime_AndState_WhenResting()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));

		verify(mockDisplay).show(REST_DURATION);
		verify(mockDisplay).showResting();
		verify(mockCounter).count(REST_DURATION);
	}

	@Test
	void shouldShowPause_DuringWork()
	{
		timer.begin(moment.current());
		timer.pause(moment.afterSeconds(3));

		verify(mockDisplay).show(3);
		verify(mockDisplay).pauseWork();
		verify(mockCounter, atMost(2)).stop();
	}

	@Test
	void shouldShowPause_DuringRest()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));
		timer.pause(moment.afterSeconds(2));

		verify(mockDisplay).show(REST_DURATION - 2);
		verify(mockDisplay).pauseRest();
		verify(mockCounter, times(2)).stop();
	}

	@Test
	void shouldShowResumingWork()
	{
		timer.begin(moment.current());
		timer.pause(moment.afterSeconds(1));
		timer.resume(moment.afterSeconds(123));

		verify(mockDisplay, atLeastOnce()).show(1);
		verify(mockDisplay, times(2)).showWorking();
		verify(mockCounter, times(2)).countUp();
	}

	@Test
	void shouldShowResumingRest()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(WORK_DURATION));
		timer.pause(moment.afterSeconds(1));
		timer.resume(moment.afterSeconds(123));

		verify(mockDisplay, atLeastOnce()).show(REST_DURATION - 1);
		verify(mockDisplay, times(2)).showResting();
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
