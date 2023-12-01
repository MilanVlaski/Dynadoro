package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.*;
import record.clock.ClockManager;

public class TestDisplayClocks
{

	@Test
	void GetsNoDays()
	{
		History history = mock(History.class);
		when(history.retrievePeriods())
		        .thenReturn(Collections.emptyList());

		ClockManager manager = new ClockManager(history);
		List<Day> days = manager.allDays();

		assertEquals(0, days.size());
	}

	@Test
	void GetsOneDay()
	{
		History history = mock(History.class);
		when(history.retrievePeriods())
		        .thenReturn(List.of(new Period(State.WORKING,
		                LocalDateTime.of(2023, 12, 1, 2, 0),
		                LocalDateTime.of(2023, 12, 1, 3, 0))));

		ClockManager manager = new ClockManager(history);
		List<Day> days = manager.allDays();

		assertEquals(1, days.size());
	}
}
