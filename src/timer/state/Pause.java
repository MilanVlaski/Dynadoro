package timer.state;

import timer.Timer;

public class Pause extends TimerState {

	private final TimerState previousState;
	private final int timeDisplayedWhenPaused;

	public Pause(Timer context, TimerState previousState, int now) {
		super(context);
		this.previousState = previousState;
		this.timeDisplayedWhenPaused = previousState.displayedTime(now);
	}

	@Override
	public int displayedTime(int now) {
		return timeDisplayedWhenPaused;
	}

	@Override
	public void begin(int now) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeBreak(int now) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause(int now) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume(int now) {
		if(previousState instanceof Working)
			context.changeState(new Working(context, now, timeDisplayedWhenPaused));
		else if (previousState instanceof TakingBreak)
			context.changeState(new TakingBreak(timeDisplayedWhenPaused, context, now));
	}

}
