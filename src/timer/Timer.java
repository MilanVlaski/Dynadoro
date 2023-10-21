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
		this.counter = counter;
		state = new Idle(this);
	}

	public int displayedTime() {
		return state.displayedTime(clock.currentTimeSeconds());
	}

	public void begin() {
		state.begin(clock.currentTimeSeconds());
		counter.countUp();
	}

	public void takeBreak() {
		int now = clock.currentTimeSeconds();
		state.takeBreak(now);
		counter.countDown(state.displayedTime(now));
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
