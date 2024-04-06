package recording;

import java.time.Duration;
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
		if(!(period.duration().compareTo(Duration.ofMinutes(1)) < 0))
			this.sessions.add(period);
	}

}
