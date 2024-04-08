package recording;

import java.util.List;

public interface History2
{
	List<Period> getSessions();
	List<Day> getDays();
	void capture(Period period);
}
