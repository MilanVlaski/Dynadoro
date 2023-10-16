package test.display;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import display.Display.DisplayState;
import timer.Clock;
import timer.Timer;

class TestDisplay {

	@Mock
	Clock mockClock = mock(Clock.class);
	@Mock
	Display mockDisplay = mock(Display.class);
	
	@InjectMocks
	Timer timer;
	
	@BeforeEach
	void injectMocks() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void shouldSetDisplayedTimeToZero() {
		verify(mockDisplay).setTime(0);
		verify(mockDisplay).setState(DisplayState.IDLE);
		verify(mockDisplay, atLeastOnce()).update();
	}

	@Test
	void shouldSetDisplayedTimeOnBegin() {
		when(mockClock.currentTimeSeconds()).thenReturn(0);
		
		timer.begin();
		
		verify(mockDisplay, atLeastOnce()).setTime(0);
		verify(mockDisplay).setState(DisplayState.WORKING);
		verify(mockDisplay, atLeastOnce()).update();
	}

	@Test
	void shouldSetDisplayedTimeOnBreak() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(25);
		
		timer.begin();
		timer.takeBreak();
		
		verify(mockDisplay).setTime(5);
		verify(mockDisplay).setState(DisplayState.TAKING_BREAK);
		verify(mockDisplay, atLeastOnce()).update();
	}
	
}
