package record;

import java.util.ArrayList;
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
			List<Day> days = new ArrayList<>();
			
			for (Period period : periods)
			{
				List<Period> periodOfDay = List.of(period);
				Day day = new Day(periodOfDay);
				days.add(day);
			}

			return days;
		}
	}

}
