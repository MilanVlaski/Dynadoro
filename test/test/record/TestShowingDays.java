package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import record.Day;
import record.History;
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
}
