package test.display;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import display.ConsoleDisplay;
import display.Display;

class TestDisplay {

	ConsoleDisplay display = new ConsoleDisplay();
	
	@Test
	void shouldDisplayFiveSeconds() {
		assertEquals("00:05", display.display(5));
	}
	
	@Test
	void shouldDisplayMinuteAndFiveSeconds() {
		assertEquals("01:05", display.display(65));
	}
	
	@Test
	void shouldDisplayOneHour() {
		assertEquals("01:00:00", display.display(3600));
	}

}
