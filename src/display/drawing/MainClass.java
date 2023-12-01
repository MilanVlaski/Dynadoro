package display.drawing;

import java.util.List;

import record.*;
import record.clock.ClockMaker;
import record.clock.ClockManager;

public class MainClass
{

	public static void main(String[] args)
	{
		UsageHistory history = new UsageHistory();
		List<Period> periods = history.retrievePeriods();
		List<Day> days = ClockManager.createDays(periods);

		for (Day day : days)
			ClockMaker.makeClock(day);

		System.out.println("Drawing Clocks was attempted.");
	}
}