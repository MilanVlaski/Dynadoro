package recording;

import java.time.*;

public class SessionRecorder
{
	private final LocalDateTime start;
	private final LocalDateTime end;
	private final History2 history;
	private final State state;

	public SessionRecorder(History2 history2, State state, LocalDateTime start,
	                       LocalDateTime end)
	{
		this.history = history2;
		this.state = state;
		this.start = start;
		this.end = end;
	}

	public void capture()
	{
		if (sessionLastsMoreThanOneDay())
			throw new SessionTooLong();
		else if (sessionPassedMidnight())
		{
			var todayAtSecBeforeMidnight = LocalDateTime.of(start.toLocalDate(),
			        LocalTime.of(23, 59, 59));
			var tomorrowAtMidnight = LocalDateTime.of(start.toLocalDate().plusDays(1),
			        LocalTime.of(0, 0));

			history.capture(new Session(state, start, todayAtSecBeforeMidnight));
			history.capture(new Session(state, tomorrowAtMidnight, end));
		}
		else if (sessionLastsMoreThanOneMinute())
			history.capture(new Session(state, start, end));
	}

	private boolean sessionLastsMoreThanOneDay()
	{ return Duration.between(start, end).toDays() > 0; }

	private boolean sessionLastsMoreThanOneMinute()
	{ return Duration.between(start, end).compareTo(Duration.ofMinutes(1)) >= 0; }

	private boolean sessionPassedMidnight()
	{ return start.toLocalDate().compareTo(end.toLocalDate()) < 0; }

	public static class SessionTooLong extends RuntimeException
	{
		SessionTooLong()
		{ super("Session longer than a day. It won't be recorded."); }
	}
}