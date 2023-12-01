package record.clock;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import record.*;

public class ClockManager
{

	private History history;

	public ClockManager(History history)
	{ this.history = history; }

	public static List<Day> createDays(List<Period> periods)
	{
		Stream<LocalDate> daysWorked = periods.stream()
		        .map(Period::date)
		        .distinct();
		return daysWorked
		        .map(day -> periodsOnDay(periods.stream(), day))
		        .map(Day::new)
		        .toList();
	}

	private static List<Period> periodsOnDay(Stream<Period> periods, LocalDate date)
	{ return periods
	        .filter(period -> period.date().equals(date))
	        .toList(); }

	public static void assignClocksToDays(List<ProductivityClock> clocks, List<Day> days)
	{
		for (Day day : days)
		{
			for (ProductivityClock clock : clocks)
			{
				LocalDate clockDate = clock.date();
				if (clockDate.equals(day.date()))
				{
					day.assignClock(clock);
					break;
				}
			}

			if (!day.hasClock())
				day.assignClock(new ProductivityClock(day));
		}
	}

	public List<Day> allDays()
	{
		List<Day> days = createDays(history.retrievePeriods());
		assignClocksToDays(history.retrieveClocks(), days);

		return days;
	}

}
