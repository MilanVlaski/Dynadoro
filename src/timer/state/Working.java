package timer.state;

import timer.Timer;

public class Working extends TimerStateI{
	
	private final int startTime;

	public Working(Timer context, int startTime) {
		super(context);
		this.startTime = startTime;
	}

	@Override
	public int time() {
		return context.currentTime() - startTime;
	}

	@Override
	public void begin() {
		throw new IllegalOperation("Timer is already running.");
	}

	@Override
	public void takeBreak() {
		context.changeState(new TakingBreak(context, context.currentTime()));
	}

}
