package timer.state;

import timer.Timer;

public class Idle extends TimerStateI{

	public Idle(Timer context) {
		super(context);
	}

	@Override
	public int time() {
		return 0;
	}

	@Override
	public void begin() {
		context.changeState(new Working(context, context.currentTime()));
	}

	@Override
	public void takeBreak() {
		throw new IllegalOperation("Can't take break if haven't started work.");
	}

}
