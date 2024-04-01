package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.*;
import java.util.List;

import org.junit.jupiter.api.Test;

import recording.History;
import recording.Period;
import recording.State;
import test.helpers.FakeHistory;

public class TestHandlingHistory
{

	@Test
	void retrievesZeroPeriods_IfHistoryIsEmpty()
	{
		History history = new FakeHistory("");
		List<Period> periods = history.retrievePeriods();
		assertEquals(0, periods.size());
	}

	@Test
	void retrievesOnePeriod_FromHistory()
	{
		Period period = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 23, 0, 50),
		        LocalDateTime.of(2023, 11, 23, 1, 0));
		History history = new FakeHistory(List.of(period));

		List<Period> periods = history.retrievePeriods();

		assertEquals(period, periods.get(0));
	}

	@Test
	void retrievesWorkAndRestPeriod_FromHistory()
	{
		Period work = new Period(State.WORKING,
		        LocalDateTime.of(2023, 11, 23, 0, 50),
		        LocalDateTime.of(2023, 11, 23, 1, 0));
		Period rest = new Period(State.RESTING,
		        LocalDateTime.of(2023, 11, 23, 1, 0),
		        LocalDateTime.of(2023, 11, 23, 1, 10));

		List<Period> periods = List.of(work, rest);
		History history = new FakeHistory(periods);

		assertEquals(periods, history.retrievePeriods());
	}

	@Test
	void RetrievesNothing_WhenYearDoesntHaveFourDigits()
	{
		History history = new FakeHistory("90-12-12, Tuesday, Work, 15:15, 20:20");
		assertEquals(0, history.retrievePeriods().size());
	}

	@Test
	void retrievesNothing_WhenWorkIsNotProperlyTyped()
	{
		History history = new FakeHistory("2022-12-12, Tuesday, WorkLLLLL, 15:15, 20:20");
		assertEquals(0, history.retrievePeriods().size());
	}

	@Test
	void IfStudiedPastMidnightPastMidnight_DurationIsStillCorrect_AndNotInversed()
	{
		List<Period> periodPastMidnight = List.of(
		        new Period(State.WORKING,
		                LocalDateTime.of(2024, Month.MARCH, 10, 23, 59),
		                LocalDateTime.of(2024, Month.MARCH, 11, 00, 1)));

		History history = new FakeHistory(periodPastMidnight);

		assertEquals(Duration.ofMinutes(2),
		        history.retrievePeriods().get(0).duration());
	}











}
