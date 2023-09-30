package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import timer.Timer;

class TestTimer {

	@Test
	void shouldBeZeroOnStartup() {
		Timer timer = new Timer();
		assertEquals(0, timer.time());
	}

}
