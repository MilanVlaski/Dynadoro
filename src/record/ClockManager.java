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
		        .map(day -> periodsOnDay(periods, day))
		        .map(Day::new)
		        .toList();
	}

	private static List<Period> periodsOnDay(List<Period> periods, LocalDate date)
	{ return periods.stream()
	        .filter(period -> period.date().equals(date))
	        .toList(); }

	public static void assignClocksToDays(List<ProductivityClock> clocks, List<Day> days)
	{
		// TODO Auto-generated method stub
	}

}
