package timer.state;

import display.Display.DisplayState;
import timer.Timer;

public class Idle extends TimerState {

	public Idle(Timer context) {
		super(context);
		display.show(0, DisplayState.IDLE);
	}

	@Override
	public int displayedTime(int now) {
		return 0;
	}

	@Override
	public void begin(int now) {
		context.changeState(new Working(context, now));
	}

	@Override
	public void takeBreak(int now) {
		throw new IllegalOperation("Can't take break if haven't started work.");
	}

	@Override
	public void pause(int now) {
		throw new IllegalOperation("Can't pause while doing nothing.");
	}

	@Override
	public void resume(int now, int pauseDuration) {
		// TODO Auto-generated method stub
		
	}

}
