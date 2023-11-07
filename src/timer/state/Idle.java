package timer.state;

import display.Display.DisplayState;
import record.StateInfo;
import timer.Timer;

public class Idle extends TimerState {

	public Idle(Timer context, int now) {
		super(context, now);
		display.show(0, DisplayState.IDLE);
		counter.stop();
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
		throw new IllegalOperationException("Can't take break if haven't started work.");
	}

	@Override
	public void pause(int now) {
		throw new IllegalOperationException("Can't pause while doing nothing.");
	}

	@Override
	public void resume(int now, int pauseDuration) {
		throw new IllegalOperationException("Can't resume while idle.");
	}

	@Override
	public StateInfo info() {
		// TODO Auto-generated method stub
		return null;
	}

}
