package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.History;
import record.Period;
import record.Period.State;
import test.helpers.FakeHistory;

public class TestShowingDays
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

		List<Period> expected = List.of(work, rest);
		History history = new FakeHistory(expected);
		List<Period> periods = history.retrievePeriods();

		assertEquals(expected, periods);
	}
}
