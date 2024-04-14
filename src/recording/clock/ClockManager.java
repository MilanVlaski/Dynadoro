package recording.clock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import recording.Day;
import recording.Session;

public class ClockManager
{

	private final ClockFileMaker fileMaker;

	public ClockManager(ClockFileMaker fileMaker)
	{
		this.fileMaker = fileMaker;
	}

	public static List<Day> createDays(List<Session> sessions)
	{
		Stream<LocalDate> daysWorked = sessions.stream()
		        .map(Session::date)
		        .distinct();
		return daysWorked
		        .map(day -> periodsOnDay(sessions.stream(), day))
		        .map(Day::new)
		        .toList();
	}

	private static List<Session> periodsOnDay(Stream<Session> sessions, LocalDate date)
	{
		return sessions
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
	                                  ArrayList<ProductivityClock> clocks)
	{
		for (Day day : days)
		{
			for (ProductivityClock clock : clocks)
			{

				if (clock.date().equals(day.date())
				        && !LocalDate.now().equals(day.date())) // its not today
				{
					day.assignClock(clock);
					clocks.remove(clock);
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

	public List<Day> allDays(List<Session> sessions, List<ProductivityClock> clocks)
	{
		List<Day> days = createDays(sessions);
		assignClocksToDays(clocks, days);

		return days.stream()
		        .sorted((d1,
		                 d2) -> (int) (d2.date().toEpochDay() - d1.date().toEpochDay()))
		        .toList();
	}

}
