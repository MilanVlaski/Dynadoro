package record.clock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import record.*;

public class ClockManager
{

	private final History history;
	private final ClockFileMaker fileMaker;

	public ClockManager(History history, ClockFileMaker fileMaker)
	{
		this.history = history;
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
	{ return periods
	        .filter(period -> period.date().equals(date))
	        .toList(); }

	public void assignClocksToDays(List<ProductivityClock> clocks, List<Day> days)
	{
		ArrayList<ProductivityClock> allClocks = new ArrayList<>(clocks);

		for (Day day : days)
		{
			for (ProductivityClock clock : allClocks)
			{
				LocalDate clockDate = clock.date();
				if (clockDate.equals(day.date()))
				{
					day.assignClock(clock);
					allClocks.remove(clock);
					break;
				}
			}

		}

		createClocksForDaysWithoutThem(days);
	}

	private void createClocksForDaysWithoutThem(List<Day> days)
	{
		for (Day day : days)
			if (!day.hasClock())
			{
				day.assignClock(new ProductivityClock(day));
				fileMaker.makeClockFile(day);
			}
	}

	public List<Day> allDays()
	{
		List<Day> days = createDays(history.retrievePeriods());
		List<ProductivityClock> clocks = history.retrieveClocks();
		assignClocksToDays(clocks, days);

		return days.stream()
		        .sorted((d1,
		                 d2) -> (int) (d2.date().toEpochDay() - d1.date().toEpochDay()))
		        .toList();
	}

}
