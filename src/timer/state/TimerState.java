package timer.state;

import display.Display;
import timer.Timer;

public abstract class TimerState {

	protected final Timer context;
	protected final Display display;

	public TimerState(Timer context) {
		this.context = context;
		this.display = context.getDisplay();
	}

	public abstract int displayedTime(int now);
	public abstract void begin(int now);
	public abstract void takeBreak(int now);

	public static class IllegalOperation extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public IllegalOperation(String message) {
			super(message);
		}
	}
}
