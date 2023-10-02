package test.display;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import display.ConsoleDisplay;
import display.Display;
import timer.SystemClock;
import timer.Timer;

class TestDisplay {

	ConsoleDisplay display = new ConsoleDisplay();
	
	@Test
	void shouldDisplayFiveSeconds() {
		assertEquals("00:05", display.displayedTime(5));
	}
	
	@Test
	void shouldDisplayMinuteAndFiveSeconds() {
		assertEquals("01:05", display.displayedTime(65));
	}
	
	@Test
	void shouldDisplayOneHour() {
		assertEquals("01:00:00", display.displayedTime(3600));
	}
}
