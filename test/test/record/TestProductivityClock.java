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
		assertEquals(1, days.get(0).numberOfPeriods());
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
		assertEquals(1, days.get(0).numberOfPeriods());
		assertEquals(1, days.get(1).numberOfPeriods());
	}

	@Test
	void CreatesOneDay_FromTwoPeriods_OnTheSameDay()
	{
		Period first = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 23, 0, 0),
		        LocalDateTime.of(2023, 11, 23, 0, 0));
		Period second = new Period(State.RESTING,
		        LocalDateTime.of(2023, 11, 23, 0, 0),
		        LocalDateTime.of(2023, 11, 23, 0, 0));
		
		List<Day> days = clockManager.createDays(List.of(first, second));
		
		assertEquals(1, days.size());
		assertEquals(2, days.get(0).numberOfPeriods());
	}
	
	@Test
	void CreatesOneDay_FromThreePeriods_OnTheSameDay()
	{
		Period first = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 23, 0, 0),
		        LocalDateTime.of(2023, 11, 23, 0, 0));
		Period second = new Period(State.RESTING,
		        LocalDateTime.of(2023, 11, 23, 0, 0),
		        LocalDateTime.of(2023, 11, 23, 0, 0));
		Period third = new Period(State.RESTING,
		        LocalDateTime.of(2023, 11, 23, 0, 0),
		        LocalDateTime.of(2023, 11, 23, 0, 0));
		
		List<Day> days = clockManager.createDays(List.of(first, second, third));
		
		assertEquals(1, days.size());
		assertEquals(3, days.get(0).numberOfPeriods());
	}
}
