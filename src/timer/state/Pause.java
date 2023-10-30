package timer.state;

import display.Display.DisplayState;
import timer.Timer;

public class Pause extends TimerState {

	private final TimerState previousState;

	public Pause(Timer context, TimerState previousState, int now) {
		super(context, now);
		this.previousState = previousState;

		sendDataToDisplay(previousState, now);
		counter.stop();
	}

	private void sendDataToDisplay(TimerState previousState, int now) {
		DisplayState displayState;
		if (previousState instanceof Working)
			displayState = DisplayState.WORK_PAUSE;
		else
			displayState = DisplayState.BREAK_PAUSE;

		display.show(displayedTime(now), displayState);
	}

	@Override
	public int displayedTime(int now) {
		return previousState.displayedTime(startTime);
	}

	@Override
	public void begin(int now) {
		context.changeState(new Working(context, now));
	}

	@Override
	public void takeBreak(int now) {
		previousState.takeBreak(now);
	}

	@Override
	public void pause(int now) {
	}

	@Override
	public void resume(int now, int pauseTime) {
		previousState.resume(now, startTime);
	}

}
