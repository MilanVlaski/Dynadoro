package helpers;

import java.util.ArrayList;
import java.util.List;

import recording.*;

/**
 * Doesn't interact with the file system, instead, stores Sessions in a list.
 */
public class FakeHistory implements History2
{

	private final List<Session> sessions = new ArrayList<>();

	@Override
	public List<Session> getSessions()
	{ return sessions; }

	@Override
	public void capture(Session session)
	{
		this.sessions.add(session);
	}

	@Override
	public List<Day> getDays()
	{
		// TODO Auto-generated method stub
		return null;
	 }

}
