package timer;

import display.Display;
import timer.state.Idle;
import timer.state.TimerState;

public class Timer {

	private final Clock clock;
	private final Display display;
	private TimerState state = new Idle(this);

	public Timer(Clock clock, Display display) {
		this.clock = clock;
		this.display = display;
	}

	public int time() {
		return state.displayedTime(clock.currentTimeSeconds());
	}

	public void begin() {
		state.begin(clock.currentTimeSeconds());
		display.setTime(0);
	}

	public void takeBreak() {
		int currentTime = clock.currentTimeSeconds();
		state.takeBreak(currentTime);
		display.setTime(state.displayedTime(currentTime));
	}

	public void changeState(TimerState newState) {
		state = newState;
	}

}
