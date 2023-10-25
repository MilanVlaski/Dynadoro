package timer.state;

import timer.Timer;

public class Pause extends TimerState {

	private final TimerState previousState;
	private final int pauseTime;
	
	public Pause(Timer context, TimerState previousState, int now) {
		super(context);
		this.pauseTime = now;
		this.previousState = previousState;
	}

	@Override
	public int displayedTime(int now) {
		return previousState.displayedTime(pauseTime);
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
		int pauseDuration = now - pauseTime;
		context.changeState(new Working(context, now - pauseDuration));
	}

}
