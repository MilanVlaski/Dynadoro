package timer.state;

import display.Display.DisplayState;
import timer.Timer;

public class TakingBreak extends TimerState{

	private final int breakDuration;
	private static final int BREAK_FACTOR = 5;

	public TakingBreak(Timer context, int now, int workDuration) {
		this(workDuration / BREAK_FACTOR, context, now);
	}
	
	private TakingBreak(int breakDuration, Timer context, int now) {
		super(context, now);
		this.breakDuration = breakDuration;
		
		display.show(breakDuration, DisplayState.TAKING_BREAK);
		counter.count(breakDuration);
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
		throw new IllegalOperationException("Already taking a break.");
	}

	@Override
	public void pause(int now) {
		context.changeState(new Pause(context, this, now));
	}

	@Override
	public void resume(int now, int pauseTime) {
		int breakDuration = displayedTime(pauseTime);
		context.changeState(new TakingBreak(breakDuration, context, now));
	}

}
