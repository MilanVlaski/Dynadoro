package timer.state;

import timer.Timer;

public abstract class TimerStateI {
	protected final Timer context;
	
	public TimerStateI(Timer context) {
		this.context = context;
	}
	
	public abstract int displayedTime(int now);
	public abstract void begin(int now);
	public abstract void takeBreak(int now);
}
