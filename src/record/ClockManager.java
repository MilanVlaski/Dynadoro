package record;

import java.util.ArrayList;
import java.util.List;

public class ClockManager
{

	public List<Day> createDays(List<Period> periods)
	{
		List<Day> days = new ArrayList<>();
		List<Period> temp = new ArrayList<>();
		for (int i = 0; i < periods.size(); i++)
		{
			if (i + 1 != periods.size() && periods.get(i).sameDayAs(periods.get(i + 1)))
			{
				temp.add(periods.get(i));
			}
			else
			{
				days.add(new Day(temp));
			}

		}

		return days;
	}

}
