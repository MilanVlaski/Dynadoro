package timer.state;

import display.Display.DisplayState;
import timer.Timer;

public class Working extends TimerState {

	private final int startTime;

	public Working(Timer context, int now) {
		super(context);
		startTime = now;
		
		display.show(0, DisplayState.WORKING);
		counter.countUp();
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

	@Override
	public void pause(int now) {
		context.changeState(new Pause(context, this, now));
	}
	
}
