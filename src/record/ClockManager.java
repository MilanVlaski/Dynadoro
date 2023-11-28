package record;

import java.util.ArrayList;
import java.util.List;

public class ClockManager
{

	public static List<Day> createDays(List<Period> periods)
	{
		List<Day> days = new ArrayList<>();
		ArrayList<Period> allPeriods = new ArrayList<>(periods);

		for (int i = 0; i < allPeriods.size(); i++)
		{
			Period period = allPeriods.get(i);
			List<Period> sameDatePeriods = new ArrayList<>();

			sameDatePeriods.add(period);

			for (int j = i + 1; j < allPeriods.size(); j++)
			{
				Period nextPeriod = allPeriods.get(j);
				if (period.isSameDateAs(nextPeriod))
				{
					sameDatePeriods.add(nextPeriod);
					allPeriods.remove(j--);
				}
				// Because periods are chronological, if the next period is on a different
				// day, that means we don't have to look through the entire list. Removing
				// the break will make this algorithm work for unsorted lists.
			}
			days.add(new Day(sameDatePeriods));
		}

		return days;
	}

	public static void assignClocksToDays(List<ProductivityClock> clocks, List<Day> days)
	{
		// TODO Auto-generated method stub
	}

}
