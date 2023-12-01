package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.*;
import record.clock.ClockManager;
import record.clock.ProductivityClock;

public class TestClockManager
{
	@Test
	void DoesntCreateDays_IfNoPeriodsAreProvided()
	{
		List<Day> days = ClockManager.createDays(Collections.emptyList());
		assertEquals(0, days.size());
	}

	static Period seventhNovember = new Period(State.WORKING,
	        LocalDateTime.of(2023, 11, 7, 0, 0),
	        LocalDateTime.of(2023, 11, 7, 0, 50));
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
	void CreatesOneDay_FromTwoPeriods_OnTheSameDay()
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

	@Test
	void AssignsClockToCorrectDay()
	{
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));
		ProductivityClock clock = new ProductivityClock(days.get(0));

		ClockManager.assignClocksToDays(List.of(clock), days);

		assertEquals(clock, days.get(0).clock());
	}

	@Test
	void CreatesNewClock_AndAssignsIt_IfIncorrectClockIsGiven()
	{
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));
		ProductivityClock clock = new ProductivityClock(Path.of("11_07_2023.lala"));

		ClockManager.assignClocksToDays(List.of(clock), days);

		ProductivityClock expected = new ProductivityClock(days.get(0));
		assertEquals(expected, days.get(0).clock());
	}

	@Test
	void CreatesNewClock_AndAssignsIt_IfNoClocksExist()
	{
		List<ProductivityClock> noClocks = Collections.emptyList();
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));

		ClockManager.assignClocksToDays(noClocks, days);

		ProductivityClock expected = new ProductivityClock(days.get(0));
		assertEquals(expected, days.get(0).clock());
	}

}
