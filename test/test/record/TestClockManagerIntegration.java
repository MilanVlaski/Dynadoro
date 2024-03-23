package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.*;
import record.clock.ClockFileMaker;
import record.clock.ClockManager;

public class TestClockManagerIntegration
{

	ClockFileMaker dummy = mock(ClockFileMaker.class);
	History history = mock(History.class);

	@Test
	void GetsNoDays()
	{
		ClockManager manager = new ClockManager(dummy);

		List<Day> days = manager.allDays(Collections.emptyList(), Collections.emptyList());

		assertEquals(0, days.size());
	}

	@Test
	void GetsOneDay()
	{
		ClockManager manager = new ClockManager(dummy);

		List<Period> twoPeriods = List.of(new Period(State.WORKING,
		        LocalDateTime.of(2023, 12, 1, 2, 0),
		        LocalDateTime.of(2023, 12, 1, 3, 0)));

		List<Day> days = manager.allDays(twoPeriods, Collections.emptyList());

		assertEquals(1, days.size());
	}
}
