package recording;

import java.util.ArrayList;
import java.util.List;

/**
 * Doesn't interact with the file system, instead, stores Sessions in a list.
 */
public class FakeHistory implements History2
{

	private final List<Period> sessions = new ArrayList<Period>();

	@Override
	public List<Period> getSessions()
	{ return sessions; }

	@Override
	public void capture(Period period)
	{
		this.sessions.add(period);
	}

}
