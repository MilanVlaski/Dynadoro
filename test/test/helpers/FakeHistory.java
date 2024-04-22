package test.helpers;

import java.util.ArrayList;
import java.util.List;

import recording.*;

/**
 * Doesn't interact with the file system, instead, stores Sessions in a list.
 */
public class FakeHistory implements History
{

	private final List<Session> sessions = new ArrayList<Session>();

	@Override
	public List<Session> getSessions()
	{ return sessions; }

	@Override
	public void write(Session session)
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
