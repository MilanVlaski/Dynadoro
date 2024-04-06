package timer.state;

import java.time.LocalDateTime;
import java.time.LocalTime;

import display.Display;
import recording.*;
import timer.Timer;
import timer.counter.Counter;

public abstract class TimerState
{

	protected final Timer context;
	protected final Display display;
	protected final Counter counter;
	protected final LocalDateTime start;

	public TimerState(Timer context, LocalDateTime now)
	{
		this.context = context;
		this.display = context.getDisplay();
		this.counter = context.getCounter();

		this.start = now;
	}

	public abstract int seconds(LocalDateTime now);
	public abstract void begin(LocalDateTime now);
	public abstract void rest(LocalDateTime now);
	public abstract void pause(LocalDateTime now);
	public abstract void resume(LocalDateTime now);
	public abstract void record(UsageRecord record);

	public static class IllegalOperationException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public IllegalOperationException(String message)
		{ super(message); }
	}

	public abstract void capture(History2 history2, LocalDateTime now);

	protected static void capture(History2 history2, State working, LocalDateTime start,
	                              LocalDateTime end)
	{
		if (sessionPassedMidnight(start, end))
		{
			var secBeforeMidnight = LocalDateTime.of(start.toLocalDate(),
			        LocalTime.of(23, 59, 59));
			var midnight = secBeforeMidnight.plusSeconds(1);

			history2.capture(new Period(working, start, secBeforeMidnight));
			history2.capture(new Period(working, midnight, end));
		}
		else
		{
			history2.capture(new Period(working, start, end));
		}
	}

	private static boolean sessionPassedMidnight(LocalDateTime start, LocalDateTime end)
	{ return start.toLocalDate().compareTo(end.toLocalDate()) < 0; }
}
