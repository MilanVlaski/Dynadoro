package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.*;

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
		ProductivityClock clock = new ProductivityClock(Path.of("07_11_2023"));
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));

		ClockManager.assignClocksToDays(List.of(clock), days);

		assertEquals(clock, days.get(0).clock());
	}

	@Test
	void CreatesNewClockAndAssignsIt_IfIncorrectClockIsGiven()
	{
		ProductivityClock clock = new ProductivityClock(Path.of("08_12_2024"));
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));

		ClockManager.assignClocksToDays(List.of(clock), days);

		ProductivityClock expected = new ProductivityClock(Path.of("07_11_2023"));
		assertEquals(expected, days.get(0).clock());
	}

	@Test
	void CreatesNewClock_AndAssignsItToDay_IfNoClocksExist()
	{
		List<ProductivityClock> noClocks = Collections.emptyList();
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));

		ClockManager.assignClocksToDays(noClocks, days);

		ProductivityClock expected = new ProductivityClock(Path.of("07_11_2023"));
		assertEquals(expected, days.get(0).clock());
	}

}
