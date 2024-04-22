package recording;

import java.util.List;

public interface History
{
	List<Session> getSessions();
	List<Day> getDays();
	void write(Session session);
}
