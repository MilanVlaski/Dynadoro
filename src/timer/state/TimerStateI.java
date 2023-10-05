package timer.state;

import timer.Timer;

public abstract class TimerStateI {
	protected final Timer context;
	
	public TimerStateI(Timer context) {
		this.context = context;
	}
	
	abstract int time();
	abstract void begin();
	abstract void takeBreak();
}
