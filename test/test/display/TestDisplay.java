package test.display;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import display.ConsoleDisplay;
import display.Display;
import timer.Clock;
import timer.Timer;

class TestDisplay {

	@Test
	void displayFiveSeconds() {
		assertEquals("00:05", ConsoleDisplay.displayedTime(5));
	}

	@Test
	void displayMinuteAndFiveSeconds() {
		assertEquals("01:05", ConsoleDisplay.displayedTime(65));
	}

	@Test
	void displayOneHour() {
		assertEquals("01:00:00", ConsoleDisplay.displayedTime(3600));
	}

	@Test
	void shouldDisplayIdleState() {
		assertEquals("00:00\nPress 1 to start studying", 
				ConsoleDisplay.displayIdle());
	}
	
	@Test
	void shouldDisplayWorkingState() {
		assertEquals("00:00\nPress 1 to take a break",
				ConsoleDisplay.displayWorking(0));
	}
	
	@Test
	void shouldDisplayBreakState() {
		assertEquals("00:00\nPress 1 to go back to work",
				ConsoleDisplay.displayBreak(0));
	}
	
	@Test
	void shouldSetDisplayedTime() {
		Clock mockClock = mock(Clock.class);
		when(mockClock.currentTimeSeconds()).thenReturn(0).thenReturn(25);
		
		Display mockDisplay = mock(Display.class);
		Timer timer = new Timer(mockClock, mockDisplay);
		
		timer.begin();
		timer.takeBreak();
		verify(mockDisplay).time = 5;
	}
} 
