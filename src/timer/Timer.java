package timer;

import timer.state.Idle;
import timer.state.TimerState;

public class Timer {

	private final Clock clock;
	private TimerState state = new Idle(this);

	public Timer(Clock clock) {
		this.clock = clock;
	}

	public int time() {
		return state.displayedTime(clock.currentTimeSeconds());
	}

	public void begin() {
		state.begin(clock.currentTimeSeconds());
	}

	public void takeBreak() {
		state.takeBreak(clock.currentTimeSeconds());
	}

	public void changeState(TimerState newState) {
		state = newState;
	}

}
