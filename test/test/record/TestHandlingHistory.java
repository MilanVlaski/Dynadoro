package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.ClockManager;
import record.Day;
import record.History;
import record.Period;
import record.State;
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

}
