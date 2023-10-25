package timer.state;

import timer.Timer;

public class Pause extends TimerState {

	private final TimerState previousState;
	private final int pauseTime;
	private final int previouslyDisplayedTime;

	public Pause(Timer context, TimerState previousState, int now) {
		super(context);
		this.pauseTime = now;
		this.previousState = previousState;
		this.previouslyDisplayedTime = previousState.displayedTime(now);
	}

	@Override
	public int displayedTime(int now) {
		return previouslyDisplayedTime;
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
		context.changeState(new Working(context, now - previouslyDisplayedTime));
	}

}
