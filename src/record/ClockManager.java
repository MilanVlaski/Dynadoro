package record;

import java.util.ArrayList;
import java.util.List;

public class ClockManager
{

	public List<Day> createDays(List<Period> periods)
	{
		List<Day> days = new ArrayList<>();
		ArrayList<Period> allPeriods = new ArrayList<>(periods);

		for (int i = 0; i < allPeriods.size(); i++)
		{
			List<Period> sameDatePeriods = new ArrayList<>();
			Period period = allPeriods.get(i);

			sameDatePeriods.add(period);

			for (int j = i + 1; j < allPeriods.size(); j++)
			{
				Period nextPeriod = allPeriods.get(j);
				if (period.isSameDateAs(nextPeriod))
				{
					sameDatePeriods.add(nextPeriod);
					allPeriods.remove(j--);
				}
 				else
					break; 
				// because periods are chronological, if the next period is on a different
				// day, that means we don't have to look through the entire list
			}

			days.add(new Day(sameDatePeriods));
		}

		return days;
	}

}
