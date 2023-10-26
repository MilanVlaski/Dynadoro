package timer.state;

import display.Display.DisplayState;
import timer.Timer;

public class Working extends TimerState {

	private final int startTime;
	private final int startFrom;

	public Working(Timer context, int now) {
		this(context, now, 0);
	}

	private Working(Timer context, int now, int startFrom) {
		super(context);
		this.startFrom = startFrom;
		startTime = now;
		
		// NOTE: this may not be correct
		display.show(displayedTime(now), DisplayState.WORKING);
		counter.countUp();
	}

	@Override
	public int displayedTime(int now) {
		return startFrom + now - startTime;
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

	@Override
	public void resume(int now, int pauseTime) {
		int startFrom = displayedTime(pauseTime);
		context.changeState(new Working(context, now, startFrom));
	}

}
