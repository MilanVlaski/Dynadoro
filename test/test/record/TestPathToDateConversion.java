package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import record.clock.ProductivityClock;

class TestPathToDateConversion
{

	@Test
	void ClockConvertsPathToDateCorrectly()
	{
		ProductivityClock clock = new ProductivityClock(
		        Path.of("C://blabla//bla//07_11_2023.blabla"));

		assertEquals(LocalDate.of(2023, 11, 7), clock.date());

	}

	@Test
	void ClockConvertsPathToDateCorrectly_IfNoFolders()
	{
		ProductivityClock clock = new ProductivityClock(
		        Path.of("07_11_2023.blabla"));

		assertEquals(LocalDate.of(2023, 11, 7), clock.date());

	}

}
