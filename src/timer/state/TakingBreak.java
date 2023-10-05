package timer.state;

import timer.Timer;

public class TakingBreak extends TimerStateI{

	private final int startTime;
	private final int breakTime;
	private static final int BREAK_TIME_FACTOR = 5;

	public TakingBreak(Timer context, int now, int workTime) {
		super(context);
		this.startTime = now;
		this.breakTime = workTime / BREAK_TIME_FACTOR;
	}

	@Override
	public int displayedTime(int now) {
		int breakRemaining = breakTime - (now - startTime);
		
		if (breakRemaining > 0)
			return breakRemaining;
		else
			return 0;
	}

	@Override
	public void begin(int now) {
		context.changeState(new Working(context, now));
	}

	@Override
	public void takeBreak(int now) {
		throw new IllegalOperation("Already taking a break.");
	}

}
