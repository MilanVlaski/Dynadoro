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
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;

class TestDisplay {

	@Mock
	Clock mockClock;
	@Mock
	Display mockDisplay;
	@Mock
	Counter mockCounter;
	
	@InjectMocks
	Timer timer;
	
	@BeforeEach
	void injectMocks() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void shouldSetDisplayedTimeToZero() {
		verify(mockDisplay).show(0, DisplayState.IDLE);
	}

	@Test
	void shouldSetDisplayedTimeOnBegin() {
		when(mockClock.currentTimeSeconds()).thenReturn(0);
		
		timer.begin();
		
		verify(mockDisplay).show(0, DisplayState.WORKING);
	}

	@Test
	void shouldSetDisplayedTimeOnBreak() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(25);
		
		timer.begin();
		timer.takeBreak();
		
		verify(mockDisplay).show(5, DisplayState.TAKING_BREAK);
	}
	
}
