package record;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class ClockManager
{

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
				day.assignClock(new ProductivityClock(day.date()));
		}
	}

}
