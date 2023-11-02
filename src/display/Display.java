package display;

import display.Display.DisplayState;
import timer.Timer;

public abstract class Display {

	public enum DisplayState {
		IDLE,
		WORKING,
		TAKING_BREAK,
		WORK_PAUSE,
		BREAK_PAUSE,
		BREAK_FINISHED;
	}

	protected int time;
	protected DisplayState state;
	protected Timer timer;

	public void setTime(int time) {
		this.time = time;
	}

	public void setState(DisplayState state) {
		this.state = state;
	}

	public void show(int displayedTime) {
		this.time = displayedTime;
		update(displayedTime);
	}

	public void show(int displayedTime, DisplayState state) {
		this.state = state;
		this.time = displayedTime;
		update(displayedTime, state);
	}

	protected abstract void update(int displayedTime);

	protected abstract void update(int displayedTime, DisplayState state);

}
