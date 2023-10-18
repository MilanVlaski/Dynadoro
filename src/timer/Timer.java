package timer;

import display.Display;
import timer.state.Idle;
import timer.state.TimerState;

public class Timer {

	private final Clock clock;
	private final Display display;
	private final ScheduledCounter counter;

	private TimerState state;

	public Timer(Clock clock, Display display, ScheduledCounter counter) {
		this.clock = clock;
		this.display = display;
		state = new Idle(this);
		this.counter = counter;
	}

	public int displayedTime() {
		return state.displayedTime(clock.currentTimeSeconds());
	}

	public void begin() {
		state.begin(clock.currentTimeSeconds());
		counter.countUp();
	}

	public void takeBreak() {
		state.takeBreak(clock.currentTimeSeconds());
	}

	public void changeState(TimerState newState) {
		state = newState;
	}

	public Display getDisplay() {
		return display;
	}

	public void updateDisplayedTime() {
		display.update(displayedTime());
	}

}
