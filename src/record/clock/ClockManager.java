package record.clock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import record.Day;
import record.Period;

public class ClockManager
{

	private final ClockFileMaker fileMaker;

	public ClockManager(ClockFileMaker fileMaker)
	{
		this.fileMaker = fileMaker;
	}

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
	{
		return periods
		        .filter(period -> period.date().equals(date))
		        .toList();
	}

	public void assignClocksToDays(List<ProductivityClock> clocks, List<Day> days)
	{
		ArrayList<ProductivityClock> allClocks = new ArrayList<>(clocks);

		assignExistingClocks(days, allClocks);

		createAndAssignClocksIfMissing(days);
	}

	private void assignExistingClocks(List<Day> days,
	                                  ArrayList<ProductivityClock> allClocks)
	{
		for (Day day : days)
		{
			for (ProductivityClock clock : allClocks)
			{

				if (clock.date().equals(day.date())
				        && !LocalDate.now().equals(day.date())) // its not today
				{
					day.assignClock(clock);
					allClocks.remove(clock);
					break;
				}
			}

		}
	}

	private void createAndAssignClocksIfMissing(List<Day> days)
	{
		for (Day day : days)
		{
			if (!day.hasClock())
			{
				day.assignClock(new ProductivityClock(day));
				fileMaker.makeClockFile(day);
			}
		}
	}

	public List<Day> allDays(List<Period> periods, List<ProductivityClock> clocks)
	{
		List<Day> days = createDays(periods);
		assignClocksToDays(clocks, days);

		return days.stream()
		        .sorted((d1,
		                 d2) -> (int) (d2.date().toEpochDay() - d1.date().toEpochDay()))
		        .toList();
	}

}
