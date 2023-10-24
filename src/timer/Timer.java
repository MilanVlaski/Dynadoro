package timer;

import display.Display;
import sound.SoundPlayer;
import timer.counter.Counter;
import timer.state.Idle;
import timer.state.TimerState;

public class Timer {

	private final Clock clock;
	private final Display display;
	private final Counter counter;

	private TimerState state;

	public Timer(Clock clock, Display display, Counter counter) {
		this.clock = clock;
		this.display = display;
		this.counter = counter;

		counter.setTimer(this);
		state = new Idle(this);
	}

	public int displayedTime() {
		return state.displayedTime(clock.currentTimeSeconds());
	}

	public void begin() {
		state.begin(clock.currentTimeSeconds());
	}

	public void pause() {
		clock.currentTimeSeconds();
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

	public void showTime() {
		display.show(displayedTime());
	}

	public Counter getCounter() {
		return counter;
	}

	public void finishBreak() {
		SoundPlayer.play();
	}

}
