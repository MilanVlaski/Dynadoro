package timer.state;

import timer.Timer;

public abstract class TimerStateI {
	protected final Timer context;
	
	public TimerStateI(Timer context) {
		this.context = context;
	}
	
	public abstract int displayedTime(int when);
	public abstract void begin(int when);
	public abstract void takeBreak(int when);
}
