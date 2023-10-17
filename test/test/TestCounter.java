package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import timer.Counter;

class TestCounter {

	@Test
	void shouldCount() {
		Counter counter = new Counter(0);
		counter.start();
		assertTrue(counter.isCounting());
	}

}
