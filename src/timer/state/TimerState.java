package timer.state;

import java.time.LocalDateTime;

import recording.History;
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
	public abstract void capture(History history, LocalDateTime now);

	public static class IllegalOperationException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;

		public IllegalOperationException(String message)
		{ super(message); }
	}


}
