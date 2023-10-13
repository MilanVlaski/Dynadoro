package timer.state;

import timer.Timer;

public class TakingBreak extends TimerState{

	private final int startTime;
	private final int breakDuration;
	private static final int BREAK_FACTOR = 5;

	public TakingBreak(Timer context, int now, int workDuration) {
		super(context);
		this.startTime = now;
		this.breakDuration = workDuration / BREAK_FACTOR;
		context.setDisplayTime(breakDuration);
	}

	@Override
	public int displayedTime(int now) {
		int remainingBreakDuration = breakDuration - (now - startTime);
		
		if (remainingBreakDuration > 0)
			return remainingBreakDuration;
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
