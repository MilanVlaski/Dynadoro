package recording;

import java.util.List;

public class RealHistory implements History2
{

	private final History history;

	public RealHistory(History history)
	{
		this.history = history;
	}

	@Override
	public List<Session> getSessions()
	{
		return history.retrievePeriods();
	}

	@Override
	public void capture(Session session)
	{
		history.write(session.toString());
	}

	@Override
	public List<Day> getDays()
	{ 
		// TODO Auto-generated method stub
		return null;
	 }

}
