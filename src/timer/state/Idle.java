package timer.state;

import timer.Timer;

public class Idle implements TimerStateI{

	private final Timer context;

	public Idle(Timer context) {
		this.context = context;
	}

	@Override
	public int time() {
		return 0;
	}

	@Override
	public void begin() {
		context.changeState(new Working(context));
	}

	@Override
	public void takeBreak() {
		throw new IllegalOperation("Can't take break if haven't started work.");
	}

}
