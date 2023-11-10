package test.display;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
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
import test.TestTimer.Moment;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;

class TestDisplay
{

	@Mock
	Clock mockClock;
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
		moment = new Moment();
	}

	@Test
	void shouldShowIdle_OnInit()
	{ verify(mockDisplay).show(0, DisplayState.IDLE); }

	@Test
	void shouldShowWorkingState_OnBegin() {
		when(mockClock.currentTimeSeconds()).thenReturn(123);
		
		timer.begin();
		
		verify(mockDisplay).show(0, DisplayState.WORKING);
		verify(mockCounter).countUp();
	}

	@Test
	void shouldShowAppropriateTime_AndState_WhenTakingBreak() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(moment.current(), moment.after(TWENTY_FIVE));
		
		timer.begin();
		timer.rest();
		
		verify(mockDisplay).show(REST_DURATION,  DisplayState.RESTING);
		verify(mockCounter).count(REST_DURATION);
	}

	@Test
	void shouldShowPause_DuringWork() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(moment.current()).thenReturn(moment.after(3));
		
		
		timer.begin();
		timer.pause();
		
		verify(mockDisplay).show(3, DisplayState.WORK_PAUSE);
		verify(mockCounter, atLeastOnce()).stop();
	}

	@Test
	void shouldShowPause_DuringBreak() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(moment.current())
			.thenReturn(moment.after(TWENTY_FIVE))
			.thenReturn(moment.after(2));
		
		
		timer.begin();
		timer.rest();
		timer.pause();  
		
		verify(mockDisplay).show(REST_DURATION - 2, DisplayState.BREAK_PAUSE);
		verify(mockCounter, atLeastOnce()).stop();
	}

	@Test
	void shouldShowResumingWork() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(moment.current(), moment.after(1), moment.after(1));
		
		
		timer.begin();
		timer.pause();
		timer.resume();
		
		verify(mockDisplay).show(1, DisplayState.WORKING);
		verify(mockCounter, times(2)).countUp();
	}

	@Test
	void shouldShowResumingBreak() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(moment.current(), moment.after(TWENTY_FIVE),
						moment.after(1), moment.after(1));

		
		timer.begin();
		timer.rest();
		timer.pause();
		timer.resume();
		
		verify(mockDisplay).show(REST_DURATION - 1, DisplayState.BREAK_PAUSE);
		verify(mockCounter).count(REST_DURATION - 1);
	}

	@Test
	void shouldStopCounter_WhenResetting()
	{
		timer.begin();
		timer.reset();

		verify(mockCounter, atLeastOnce()).stop();
	}
}
