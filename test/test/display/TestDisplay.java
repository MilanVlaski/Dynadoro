package test.display;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import display.ConsoleDisplay;
import display.Display;
import timer.SystemClock;
import timer.Timer;
import timer.Timer.TimerState;

class TestDisplay {

	ConsoleDisplay display = new ConsoleDisplay();
	
	@Test
	void displayFiveSeconds() {
		assertEquals("00:05", display.displayedTime(5));
	}
	
	@Test
	void displayMinuteAndFiveSeconds() {
		assertEquals("01:05", display.displayedTime(65));
	}
	
	@Test
	void displayOneHour() {
		assertEquals("01:00:00", display.displayedTime(3600));
	}
	
	@Test
	void displayOnStartup() {
		assertEquals("00:00\nPress 1 to start studying.",
				display.displayedMessage(123, TimerState.IDLE));
	}
	
	@Test
	void displayWhenStudying() {
		assertEquals("00:21\nPress 1 to take a break.",
				display.displayedMessage(21, TimerState.WORKING));
	}
}
