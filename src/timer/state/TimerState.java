package timer.state;

import display.Display;
import record.StateInfo;
import timer.Timer;
import timer.counter.Counter;

public abstract class TimerState {

	protected final Timer context;
	protected final Display display;
	protected final Counter counter;
	
	protected final int startTime;

	public TimerState(Timer context, int now) {
		this.context = context;
		this.display = context.getDisplay();
		this.counter = context.getCounter();
		
		this.startTime = now;
	}

	public abstract int displayedTime(int now);
	public abstract void begin(int now);
	public abstract void takeBreak(int now);
	public abstract void pause(int now);
	public abstract void resume(int now, int pauseTime);
	public abstract StateInfo info();

	public static class IllegalOperationException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public IllegalOperationException(String message) {
			super(message);
		}
	}


}
