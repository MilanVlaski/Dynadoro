package timer.state;

import timer.Timer;

public class Working extends TimerStateI {

	private final int startTime;

	public Working(Timer context, int when) {
		super(context);
		this.startTime = when;
	}

	@Override
	public int displayedTime(int when) {
		return when - startTime;
	}

	@Override
	public void begin(int when) {
		throw new IllegalOperation("Timer is already running.");
	}

	@Override
	public void takeBreak(int when) {
		int workTime = displayedTime(when);
		context.changeState(new TakingBreak(context, when, workTime));
	}

}
