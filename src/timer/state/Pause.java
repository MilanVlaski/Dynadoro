package timer.state;

import display.Display.DisplayState;
import timer.Timer;

public class Pause extends TimerState {

	private final TimerState previousState;

	public Pause(Timer context, TimerState previousState, int now) {
		super(context, now);
		this.previousState = previousState;
		
		counter.stop();
		display.show(displayedTime(now), DisplayState.PAUSED);
	}

	@Override
	public int displayedTime(int now) {
		return previousState.displayedTime(startTime);
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
	public void resume(int now, int pauseTime) {
		previousState.resume(now, startTime);
	}

}
