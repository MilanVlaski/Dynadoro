package test.display;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static test.TestTimer.REST_DURATION;
import static test.TestTimer.TWENTY_FIVE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import display.Display.DisplayState;
import test.Moment;
import test.TestRedesignedTimer;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;

public class TestRedesignedDisplay
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
		moment = new Moment(TestRedesignedTimer.TIME);
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
//
//	@Test
//	void shouldShowAppropriateTime_AndState_WhenResting() {
//		
//		timer.begin();
//		timer.rest();
//		
//		verify(mockDisplay).show(REST_DURATION,  DisplayState.RESTING);
//		verify(mockCounter).count(REST_DURATION);
//	}
//
//	@Test
//	void shouldShowPause_DuringWork() {
//		
//		
//		timer.begin();
//		timer.pause();
//		
//		verify(mockDisplay).show(3, DisplayState.WORK_PAUSE);
//		verify(mockCounter, atLeastOnce()).stop();
//	}
//
//	@Test
//	void shouldShowPause_DuringRest() {
//		
//		
//		timer.begin();
//		timer.rest();
//		timer.pause();  
//		
//		verify(mockDisplay).show(REST_DURATION - 2, DisplayState.REST_PAUSE);
//		verify(mockCounter, atLeastOnce()).stop();
//	}
//
//	@Test
//	void shouldShowResumingWork() {
//		
//		
//		timer.begin();
//		timer.pause();
//		timer.resume();
//		
//		verify(mockDisplay).show(1, DisplayState.WORKING);
//		verify(mockCounter, times(2)).countUp();
//	}
//
//	@Test
//	void shouldShowResumingRest() {
//
//		timer.begin();
//		timer.rest();
//		timer.pause();
//		timer.resume();
//		
//		verify(mockDisplay).show(REST_DURATION - 1, DisplayState.REST_PAUSE);
//		verify(mockCounter).count(REST_DURATION - 1);
//	}
//
//	@Test
//	void shouldStopCounter_WhenResetting()
//	{
//		timer.begin();
//		timer.reset();
//
//		verify(mockCounter, atLeastOnce()).stop();
//	}
}
