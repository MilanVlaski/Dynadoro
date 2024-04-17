package recording;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class Day
{
	private final List<Session> sessions;
	private final LocalDate date;

	public Day(List<Session> sessions)
	{
		this.sessions = sessions;
		if (!sessions.isEmpty())
			this.date = sessions.get(0).date();
		else
			throw new IllegalArgumentException("A day must have at least one session!");
	}

	public int numberOfPeriods()
	{ return sessions.size(); }

	public LocalDate date()
	{ return date; }

	public Duration timeWorked()
	{ return timeSpent(State.WORKING); }

	public Duration timeRested()
	{ return timeSpent(State.RESTING); }

	private Duration timeSpent(State state)
	{
		return sessions.stream()
		               .filter(p -> p.type().equals(state))
		               .map(Session::duration)
		               .reduce(Duration::plus)
		               .orElse(Duration.ZERO);
	}

	public List<Session> sessions()
	{
		return sessions;
	}

}
