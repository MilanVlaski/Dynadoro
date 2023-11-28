package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.*;
import test.helpers.FakeHistory;

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
	        LocalDateTime.of(2023, 11, 7, 0, 0));
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
	void CreatesThreeDays_IfPeriodDaysAreNotChronological()
	{
		// If this test fails, it means you can use unsorted data as input! Then you
		// may comment the test out, or change the assertion to match the behavior you
		// want.
		List<Day> days = ClockManager.createDays(
		        List.of(dayOnePeriod, dayTwoPeriod, dayOnePeriod));

		assertEquals(2, days.size());
	}

	@Test
	void EmptyClocksAndDays()
	{
		History history = new FakeHistory(); // put clocks and days in here
		List<Period> periods = history.retrievePeriods();
		List<Day> days = ClockManager.createDays(periods);
		List<ProductivityClock> clocks = history.retrieveClocks();
		ClockManager.assignClocksToDays(clocks, days);

		assertEquals(0, days.size());
	}
}
