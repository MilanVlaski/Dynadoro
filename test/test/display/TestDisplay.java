package test.display;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
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
		verify(mockDisplay).show(anyInt(), eq(DisplayState.IDLE));
	}

	@Test
	void shouldSetDisplayedTimeOnBegin() {
		timer.begin();
		verify(mockDisplay).show(anyInt(), eq(DisplayState.WORKING));		
	}

	@Test
	void shouldSetDisplayedTimeOnBreak() {
		timer.begin();
		timer.takeBreak();
		
		verify(mockDisplay).show(anyInt(), eq(DisplayState.TAKING_BREAK));
	}
	
//	@Test
//	void shouldPause() {
//		timer.begin();
//		timer.pause();
//		
//		verify(mockDisplay).show(5, DisplayState.TAKING_BREAK);
//	}
	
}
