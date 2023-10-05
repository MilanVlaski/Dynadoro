package timer.state;

import timer.Timer;

public abstract class TimerState {
	protected final Timer context;
	
	public TimerState(Timer context) {
		this.context = context;
	}
	
	public abstract int displayedTime(int now);
	public abstract void begin(int now);
	public abstract void takeBreak(int now);
	
	public class IllegalOperation extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public IllegalOperation(String message) {
			super(message);
		}
	}
}
