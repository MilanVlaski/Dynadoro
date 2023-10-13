package timer;

import display.Display;
import timer.state.Idle;
import timer.state.TimerState;

public class Timer {

	private final Clock clock;
	private final Display display;
	private TimerState state;

	public Timer(Clock clock, Display display) {
		this.clock = clock;
		this.display = display;
		state = new Idle(this);
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

	public void setDisplayTime(int time) {
		display.setTime(time);
	}

	public void changeState(TimerState newState) {
		state = newState;
	}
}
