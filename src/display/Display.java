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

	public void show(int displayedTime) {
		this.time = displayedTime;
		updateTime(displayedTime);
	}

	public void show(int displayedTime, DisplayState state) {
		this.state = state;
		this.time = displayedTime;
		updateTimeAndState(displayedTime, state);
	}

	protected void updateTimeAndState(int displayedTime, DisplayState state) {
		updateTime(displayedTime);
		updateState(state);
	}

	protected void updateState(DisplayState state) {
		switch (state) {
		case IDLE:
			showIdle();
			break;
		case WORKING:
			showWorking();
			break;
		case TAKING_BREAK:
			showBreak();
			break;
		case BREAK_FINISHED:
			finishBreak();
			break;
		case BREAK_PAUSE:
			pauseBreak();
			break;
		case WORK_PAUSE:
			pauseWork();
			break;
		}
	}

	protected abstract void updateTime(int displayedTime);

	protected abstract void pauseWork();

	protected abstract void pauseBreak();

	protected abstract void finishBreak();

	protected abstract void showBreak();

	protected abstract void showWorking();

	protected abstract void showIdle();

}
