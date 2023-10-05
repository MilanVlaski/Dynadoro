package timer.state;

import timer.Timer;

public class Working extends TimerState {

	private final int startTime;

	public Working(Timer context, int now) {
		super(context);
		this.startTime = now;
	}

	@Override
	public int displayedTime(int now) {
		return now - startTime;
	}

	@Override
	public void begin(int now) {
		throw new IllegalOperation("Timer is already running.");
	}

	@Override
	public void takeBreak(int now) {
		int workDuration = displayedTime(now);
		context.changeState(new TakingBreak(context, now, workDuration));
	}

}
