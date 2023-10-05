package timer.state;

import timer.Timer;

public class Idle extends TimerState{

	public Idle(Timer context) {
		super(context);
	}

	@Override
	public int displayedTime(int now) {
		return 0;
	}

	@Override
	public void begin(int now) {
		context.changeState(new Working(context, now));		
	}

	@Override
	public void takeBreak(int now) {
		throw new IllegalOperation("Can't take break if haven't started work.");
	}

}
