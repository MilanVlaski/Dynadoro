package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import record.Day;
import record.History;
import record.StateData;
import record.StateData.State;
import test.helpers.FakeHistory;

public class TestShowingDays
{

	@Test
	void retrievesZeroDays_IfHistoryIsEmpty()
	{
		History history = new FakeHistory("");
		List<Day> days = history.retrieveDays();
		assertEquals(0, days.size());
	}

	@Test
	void retrievesOneDay_FromHistory()
	{
		StateData state = new StateData(State.WORK,
		        LocalDateTime.of(2023, 11, 23, 0, 50),
		        LocalDateTime.of(2023, 11, 23, 1, 0));
		History history = new FakeHistory(List.of(state));
		
		List<Day> days = history.retrieveDays();
		assertEquals(1, days.size());
	}
}
