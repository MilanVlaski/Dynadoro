package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.*;

public class TestProductivityClock
{
	@Test
	void DoesntCreateDays_IfNoPeriodsAreProvided()
	{
		List<Day> days = ClockManager.createDays(Collections.emptyList());
		assertEquals(0, days.size());
	}

	static Period dayOnePeriod = new Period(State.WORKING,
	        LocalDateTime.of(2023, 11, 7, 0, 0),
	        LocalDateTime.of(2023, 11, 7, 0, 50));
	static Period dayTwoPeriod = new Period(State.WORKING,
	        LocalDateTime.of(2023, 6, 19, 0, 0),
	        LocalDateTime.of(2023, 6, 19, 0, 0));

	@Test
	void CreatesDay_FromPeriodThatBelongsToIt()
	{
		List<Day> days = ClockManager.createDays(List.of(dayOnePeriod));

		assertEquals(1, days.size());
		assertEquals(1, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesTwoDays_FromTwoPeriodsBelongingToDifferentDays()
	{
		List<Day> days = ClockManager.createDays(List.of(dayOnePeriod, dayTwoPeriod));

		assertEquals(2, days.size());
		assertEquals(1, days.get(0).numberOfPeriods());
		assertEquals(1, days.get(1).numberOfPeriods());
	}

	@Test
	void CreatesOneDay_FromTwoPeriods_OnTheSameDay()
	{
		List<Day> days = ClockManager.createDays(List.of(dayOnePeriod, dayOnePeriod));

		assertEquals(1, days.size());
		assertEquals(2, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesOneDay_FromThreePeriods_OnTheSameDay()
	{
		List<Day> days = ClockManager
		        .createDays(List.of(dayOnePeriod, dayOnePeriod, dayOnePeriod));

		assertEquals(1, days.size());
		assertEquals(3, days.get(0).numberOfPeriods());
	}

	@Test
	void CreatesTwoDays_FromTwoPeriodsEach()
	{
		List<Day> days = ClockManager.createDays(
		        List.of(dayOnePeriod, dayOnePeriod, dayTwoPeriod, dayTwoPeriod));

		assertEquals(2, days.size());
		assertEquals(2, days.get(0).numberOfPeriods());
		assertEquals(2, days.get(1).numberOfPeriods());
	}

	@Test
	void CreatesTwoDays_EvenIfPeriodDaysAreNotChronological()
	{
		List<Day> days = ClockManager.createDays(
		        List.of(dayOnePeriod, dayTwoPeriod, dayOnePeriod));

		assertEquals(2, days.size());
	}

	@Test
	void AssignsClockToCorrectDay()
	{
		Period period = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 7, 0, 0),
		        LocalDateTime.of(2023, 11, 7, 0, 50));

		ProductivityClock clock = new ProductivityClock(Path.of("07_11_2023"));
		List<Day> days = ClockManager.createDays(List.of(period));

		ClockManager.assignClocksToDays(List.of(clock), days);

		assertEquals(clock, days.get(0).clock());
	}

	@Test
	void DoesNotAssignClock_ToDay_IfDifferentDates()
	{
		Period period = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 7, 0, 0),
		        LocalDateTime.of(2023, 11, 7, 0, 50));

		ProductivityClock clock = new ProductivityClock(Path.of("08_12_2024"));
		List<Day> days = ClockManager.createDays(List.of(period));

		ClockManager.assignClocksToDays(List.of(clock), days);

		assertFalse(days.get(0).hasClock());
	}

//	@Test
//	void CreatesNewClock_AndAssignsItToDay_IfNoClocksExist()
//	{
//		List<ProductivityClock> noClocks = Collections.emptyList();
//		List<Day> days = ClockManager.createDays(List.of(dayOnePeriod));
//
//		ClockManager.assignClocksToDays(noClocks, days);
//
//		ProductivityClock expected = new ProductivityClock(Path.of("07-11-2023"));
//		assertEquals(expected, days.get(0).clock());
//	}

}
