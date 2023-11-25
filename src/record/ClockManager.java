package record;

import java.util.Collections;
import java.util.List;

public class ClockManager
{

	public List<Day> createDays(List<Period> periods)
	{
		if (periods.isEmpty())
			return Collections.emptyList();
		else
		{
			Period firstPeriod = periods.get(0);
			List<Period> periodOfDay = List.of(firstPeriod);
			Day day = new Day(periodOfDay);
			return List.of(day);
		}
	}

}
