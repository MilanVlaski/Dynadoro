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

	static Period dayOnePeriod = new Period(State.WORKING,
	        LocalDateTime.of(2023, 11, 7, 0, 0),
	        LocalDateTime.of(2023, 11, 7, 0, 0));
	static Period dayTwoPeriod = new Period(State.WORKING,
	        LocalDateTime.of(2023, 6, 19, 0, 0),
	        LocalDateTime.of(2023, 6, 19, 0, 0));

	@Test
	void CreatesDay_FromPeriodThatBelongsToIt()
	{
		List<Day> days = clockManager.createDays(List.of(dayOnePeriod));

		assertEquals(1, days.size());
		assertEquals(1, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesTwoDays_FromTwoPeriodsBelongingToDifferentDays()
	{
		List<Day> days = clockManager.createDays(List.of(dayOnePeriod, dayTwoPeriod));

		assertEquals(2, days.size());
		assertEquals(1, days.get(0).numberOfPeriods());
		assertEquals(1, days.get(1).numberOfPeriods());
	}

	@Test
	void CreatesOneDay_FromTwoPeriods_OnTheSameDay()
	{
		List<Day> days = clockManager.createDays(List.of(dayOnePeriod, dayOnePeriod));

		assertEquals(1, days.size());
		assertEquals(2, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesOneDay_FromThreePeriods_OnTheSameDay()
	{
		List<Day> days = clockManager
		        .createDays(List.of(dayOnePeriod, dayOnePeriod, dayOnePeriod));

		assertEquals(1, days.size());
		assertEquals(3, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesTwoDays_FromTwoPeriodsEach()
	{
		List<Day> days = clockManager.createDays(
		        List.of(dayOnePeriod, dayOnePeriod, dayTwoPeriod, dayTwoPeriod));

		assertEquals(2, days.size());
		assertEquals(2, days.get(0).numberOfPeriods());
		assertEquals(2, days.get(1).numberOfPeriods());
	}

	@Test
	void CreatesThreeDays_FromMixedUpPeriods()
	{
		Period dayThreePeriod = new Period(State.WORKING,
		        LocalDateTime.of(2023, 1, 4, 0, 0),
		        LocalDateTime.of(2023, 1, 4, 0, 0));
		
		List<Day> days = clockManager.createDays(
		        List.of(dayThreePeriod, dayOnePeriod, dayThreePeriod,
		                dayTwoPeriod, dayTwoPeriod, dayThreePeriod,
		                dayOnePeriod, dayThreePeriod, dayOnePeriod,
		                dayThreePeriod, dayTwoPeriod, dayTwoPeriod));

		assertEquals(3, days.size());
		assertEquals(5, days.get(0).numberOfPeriods());
		assertEquals(3, days.get(1).numberOfPeriods());
		assertEquals(4, days.get(2).numberOfPeriods());
	}
}
