package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import timer.Timer;

class TestTimer {

	@Test
	void shouldStartWithZero() {
		Timer timer = new Timer();
		assertEquals(0, timer.time());
	}
	
	@Test
	void shouldMeasureElapsedTime() {
		
		Timer timer = new Timer();
		timer.begin();
		assertEquals(5, timer.end());
	}
}
