package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.*;
import record.clock.ClockManager;

public class TestClockManager
{

	@Test
	void DoesNotCreateDays_IfNoPeriodsAreProvided()
	{
		List<Day> days = ClockManager.createDays(Collections.emptyList());
		assertEquals(0, days.size());
	}

	static Period seventhNovember = new Period(State.WORKING,
	        LocalDateTime.of(2023, 11, 7, 0, 0),
	        LocalDateTime.of(2023, 11, 7, 0, 0));
	static Period nineteenthJune = new Period(State.WORKING,
	        LocalDateTime.of(2023, 6, 19, 0, 0),

	        LocalDateTime.of(2023, 6, 19, 0, 0));

	@Test
	void CreatesDay_FromPeriodThatBelongsToIt()
	{
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));

		assertEquals(1, days.size());
		assertEquals(1, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesTwoDays_FromTwoPeriodsBelongingToDifferentDays()
	{
		List<Day> days = ClockManager
		        .createDays(List.of(seventhNovember, nineteenthJune));

		assertEquals(2, days.size());
		assertEquals(1, days.get(0).numberOfPeriods());
		assertEquals(1, days.get(1).numberOfPeriods());
	}

	@Test
	void CreatesOneDay_FromTwoPeriods_BelongingToTheSameDay()
	{
		List<Day> days = ClockManager
		        .createDays(List.of(seventhNovember, seventhNovember));

		assertEquals(1, days.size());
		assertEquals(2, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesOneDay_FromThreePeriods_OnTheSameDay()
	{
		List<Day> days = ClockManager
		        .createDays(List.of(seventhNovember, seventhNovember, seventhNovember));

		assertEquals(1, days.size());
		assertEquals(3, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesTwoDays_FromTwoPeriodsEach()
	{
		List<Day> days = ClockManager.createDays(
		        List.of(seventhNovember, seventhNovember, nineteenthJune,
		                nineteenthJune));

		assertEquals(2, days.size());
		assertEquals(2, days.get(0).numberOfPeriods());
		assertEquals(2, days.get(1).numberOfPeriods());
	}

	@Test
	void CreatesTwoDays_EvenIfPeriodDaysAreNotChronological()
	{
		List<Day> days = ClockManager.createDays(
		        List.of(seventhNovember, nineteenthJune, seventhNovember));

		assertEquals(2, days.size());
	}

}
