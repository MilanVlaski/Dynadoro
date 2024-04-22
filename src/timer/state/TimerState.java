package timer.state;

import java.time.*;

import recording.*;
import timer.Timer;

public abstract class TimerState
{

	protected final Timer context;
	protected final LocalDateTime start;

	public TimerState(Timer context, LocalDateTime now)
	{
		this.context = context;
		this.start = now;
	}

	public abstract int seconds(LocalDateTime now);
	public abstract void begin(LocalDateTime now);
	public abstract void rest(LocalDateTime now);
	public abstract void pause(LocalDateTime now);
	public abstract void resume(LocalDateTime now);
	public abstract void capture(History2 history2, LocalDateTime now);

	public static class IllegalOperationException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public IllegalOperationException(String message)
		{ super(message); }
	}

	static void capture(History2 history2, State state, LocalDateTime start,
	                    LocalDateTime end)
	{
		if (sessionLastsMoreThanOneDay(start, end))
			throw new SessionTooLong();
		else if (sessionPassedMidnight(start, end))
		{
			var secBeforeMidnight = LocalDateTime.of(start.toLocalDate(),
			                                         LocalTime.of(23, 59, 59));
			var midnight = LocalDateTime.of(start.toLocalDate().plusDays(1),
			                                LocalTime.of(0, 0));

			history2.capture(new Session(state, start, secBeforeMidnight));
			history2.capture(new Session(state, midnight, end));
		}
		else if (sessionLastsMoreThanOneMinute(start, end))
			history2.capture(new Session(state, start, end));
	}

	private static boolean sessionLastsMoreThanOneDay(LocalDateTime start,
	                                                  LocalDateTime end)
	{ return Duration.between(start, end).toDays() > 0; }

	private static boolean sessionLastsMoreThanOneMinute(LocalDateTime start,
	                                                     LocalDateTime end)
	{ return Duration.between(start, end).compareTo(Duration.ofMinutes(1)) >= 0; }

	private static boolean sessionPassedMidnight(LocalDateTime start, LocalDateTime end)
	{ return start.toLocalDate().compareTo(end.toLocalDate()) < 0; }

	public static class SessionTooLong extends RuntimeException
	{
		SessionTooLong()
		{ super("Session longer than a day. It won't be recorded."); }
	}
}
