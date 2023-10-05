package timer;

import timer.state.Idle;
import timer.state.TimerStateI;

public class Timer {

	private final Clock clock;
	private TimerStateI state = new Idle(this);

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

	public void changeState(TimerStateI newState) {
		this.state = newState;
	}

}
