package test.display;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import display.ConsoleDisplay;

class TestConsoleDisplay {

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
				ConsoleDisplay.idleMessage());
	}

	@Test
	void shouldDisplayWorkingState() {
		assertEquals("00:00\nPress 2 to take a break"
				+ "\nPress 3 to pause",
				ConsoleDisplay.workingMessage(0));
	}

	@Test
	void shouldDisplayBreakState() {
		assertEquals("00:00\nPress 1 to go back to work"
				+ "\nPress 3 to pause",
				ConsoleDisplay.breakMessage(0));
	}

	@Test
	void shouldDisplayWorkPause() {
		assertEquals("00:00\nPress 4 to resume"
				+ "\nPress 2 to take a break",
				ConsoleDisplay.workPauseMessage(0));
	}
	
	@Test
	void shouldDisplayBreakPause() {
		assertEquals("00:00\nPress 4 to resume",
				ConsoleDisplay.breakPauseMessage(0));
	}

}
