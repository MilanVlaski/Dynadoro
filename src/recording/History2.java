package recording;

import java.util.List;

public interface History2
{
	List<Session> getSessions();
	List<Day> getDays();
	void capture(Session session);
}
