package test.display;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import display.Display.DisplayState;
import test.TestTimer;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;

class TestDisplay {

	@Mock
	Clock mockClock;
	@Mock
	Display mockDisplay;
	@Mock
	Counter dummyCounter;

	@InjectMocks
	Timer timer;

	@BeforeEach
	void injectMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldShowIdle_OnInit() {
		verify(mockDisplay).show(0, DisplayState.IDLE);
	}

	@Test
	void shouldShowWorkingState_OnBegin() {
		when(mockClock.currentTimeSeconds()).thenReturn(0);
		
		timer.begin();
		
		verify(mockDisplay).show(0, DisplayState.WORKING);
	}

	@Test
	void shouldShowAppropriateTime_AndState_WhenTakingBreak() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0)
			.thenReturn(TestTimer.TWENTY_FIVE);
		
		timer.begin();
		timer.takeBreak();
		
		verify(mockDisplay).show(TestTimer.BREAK_DURATION,  DisplayState.TAKING_BREAK);
	}

	@Test
	void shouldShowPause_DuringWork() {
		when(mockClock.currentTimeSeconds()).thenReturn(5).thenReturn(10);
		
		timer.begin();
		timer.pause();
		
		verify(mockDisplay).show(5, DisplayState.WORK_PAUSE);
	}

	@Test
	void shouldShowPause_DuringBreak() {
		when(mockClock.currentTimeSeconds())
			.thenReturn(0)
			.thenReturn(TestTimer.TWENTY_FIVE)
			.thenReturn(TestTimer.TWENTY_FIVE + 2);
		
		timer.begin();
		timer.takeBreak();
		timer.pause();
		
		verify(mockDisplay).show(TestTimer.BREAK_DURATION - 2, DisplayState.BREAK_PAUSE);
	}

}
