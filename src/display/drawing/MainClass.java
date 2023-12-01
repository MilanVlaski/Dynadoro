package display.drawing;

import java.util.List;

import record.*;

public class MainClass
{

	public static void main(String[] args)
	{
//		LocalDateTime zero = LocalDateTime.of(2023, 11, 18, 0, 0);
//		LocalDateTime twentyFive = LocalDateTime.of(2023, 11, 18, 0, 25);
//		LocalDateTime thirty = LocalDateTime.of(2023, 11, 18, 0, 30);
//
//		LocalDateTime fourPM = LocalDateTime.of(2023, 11, 18, 16, 0);
//		LocalDateTime fourFiftyPM = LocalDateTime.of(2023, 11, 18, 16, 50);
//		LocalDateTime fivePM = LocalDateTime.of(2023, 11, 18, 19, 0);
//
//		Day day = new Day(List.of(
//		        new Period(State.WORKING, zero, twentyFive),
//		        new Period(State.RESTING, twentyFive, thirty),
//		        new Period(State.WORKING, fourPM, fourFiftyPM),
//		        new Period(State.RESTING, fourFiftyPM, fivePM)));

		UsageHistory history = new UsageHistory();
		List<Period> periods = history.retrievePeriods();
		List<Day> days = ClockManager.createDays(periods);

		for (Day day : days)
			ClockMaker.makeClock(day);

		System.out.println("Drawing Clocks was attempted.");
//		new MyFrame(day);
	}
}