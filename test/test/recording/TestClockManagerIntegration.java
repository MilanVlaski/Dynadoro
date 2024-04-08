package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import recording.*;
import recording.clock.ClockFileMaker;
import recording.clock.ClockManager;

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
		        LocalDate.of(2023, 12, 1),
		        LocalTime.of(2, 0),
		        LocalTime.of(3, 0)
		        ));

		List<Day> days = manager.allDays(twoPeriods, Collections.emptyList());

		assertEquals(1, days.size());
	}
}
