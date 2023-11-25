package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import record.ClockManager;
import record.Day;
import record.Period;
import record.State;

public class TestProductivityClock
{
	ClockManager clockManager;

	@BeforeEach
	void setup()
	{ clockManager = new ClockManager(); }

	@Test
	void DoesntCreateDays_IfNoPeriodsAreProvided()
	{
		List<Day> days = clockManager.createDays(Collections.emptyList());
		assertEquals(0, days.size());
	}

	@Test
	void CreatesDay_FromPeriodThatBelongsToIt()
	{
		Period work = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 23, 0, 50),
		        LocalDateTime.of(2023, 11, 23, 1, 0));

		List<Day> days = clockManager.createDays(List.of(work));

		assertEquals(1, days.size());
	}

	@Test
	void CreatesTwoDays_FromTwoPeriodsBelongingToDifferentDays()
	{
		Period dayOnePeriod = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 23, 0, 50),
		        LocalDateTime.of(2023, 11, 23, 1, 0));
		Period dayTwoPeriod = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 24, 0, 50),
		        LocalDateTime.of(2023, 11, 24, 1, 0));

		List<Day> days = clockManager.createDays(List.of(dayOnePeriod, dayTwoPeriod));

		assertEquals(2, days.size());
	}
}
