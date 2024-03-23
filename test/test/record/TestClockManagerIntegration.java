package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
		ClockManager manager = new ClockManager(history, dummy);
		List<Day> days = manager.allDays(Collections.emptyList(), history.retrieveClocks());

		assertEquals(0, days.size());
	}

	@Test
	void GetsOneDay()
	{
		when(history.retrievePeriods())
		        .thenReturn(List.of(new Period(State.WORKING,
		                LocalDateTime.of(2023, 12, 1, 2, 0),
		                LocalDateTime.of(2023, 12, 1, 3, 0))));

		ClockManager manager = new ClockManager(history, dummy);
		List<Day> days = manager.allDays(history.retrievePeriods(), history.retrieveClocks());

		assertEquals(1, days.size());
	}
}
