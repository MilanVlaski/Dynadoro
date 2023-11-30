package timer.state;

import java.time.LocalDateTime;

import display.Display;
import record.UsageRecord;
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

}
