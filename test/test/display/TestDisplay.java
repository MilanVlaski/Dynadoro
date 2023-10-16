package test.display;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
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
	}

	@Test
	void shouldSetDisplayedTimeOnBegin() {
		when(mockClock.currentTimeSeconds()).thenReturn(0);
		
		timer.begin();
		verify(mockDisplay, times(2)).setTime(0);
	}

	@Test
	void shouldSetDisplayedTimeOnBreak() {
		when(mockClock.currentTimeSeconds())
						.thenReturn(0)
						.thenReturn(25);
		
		timer.begin();
		timer.takeBreak();
		verify(mockDisplay).setTime(5);
	}
}
