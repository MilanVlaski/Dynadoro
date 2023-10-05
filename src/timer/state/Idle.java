package timer.state;

import timer.Timer;

public class Idle extends TimerStateI{

	public Idle(Timer context) {
		super(context);
	}

	@Override
	public int displayedTime(int when) {
		return 0;
	}

	@Override
	public void begin(int when) {
		context.changeState(new Working(context, when));		
	}

	@Override
	public void takeBreak(int when) {
		throw new IllegalOperation("Can't take break if haven't started work.");
	}

}
