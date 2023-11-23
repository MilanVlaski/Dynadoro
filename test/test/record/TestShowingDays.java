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

	// There is an intermediate step where, when parsing, we load all of the StateData.
	// which is different than assigning that statedata to days.
	//This test does two things in one, so its bad.
	@Test
	void retrievesOneDay_FromHistory()
	{
		StateData state = new StateData(State.WORKING,
		        LocalDateTime.of(2023, 11, 23, 0, 50),
		        LocalDateTime.of(2023, 11, 23, 1, 0));
		History history = new FakeHistory(List.of(state));
		
		List<Day> days = history.retrieveDays();
		assertEquals(1, days.size());
	}
}
