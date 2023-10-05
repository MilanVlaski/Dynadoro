package timer;

import timer.state.TimerStateI;
import timer.state.Working;

public class Timer {

	public enum TimerState {
		WORKING,
		TAKING_BREAK,
		// PAUSED,
		IDLE;
	}

	private final Clock clock;
	
	private TimerState timerState = TimerState.IDLE;
	private int startTime;
	private int pauseTime;
	private static final int BREAK_TIME_FACTOR = 5;

	public Timer(Clock clock) {
		this.clock = clock;
	}

	public int time() {

		int currentTime = currentTime();
		int elapsedTime = currentTime - startTime;

		switch (timerState) {
		case WORKING:
			return elapsedTime;
		case TAKING_BREAK:
			int breakRemaining = elapsedTime / BREAK_TIME_FACTOR - (currentTime - pauseTime);
			if (breakRemaining > 0)
				return breakRemaining;
		default:
			return 0;
		}
	}

	public void begin() {
		startTime = currentTime();
		timerState = TimerState.WORKING;
	}

	public int currentTime() {
		return clock.currentTimeSeconds();
	}

	public void takeBreak() {
		timerState = TimerState.TAKING_BREAK;
		pauseTime = currentTime();
	}

	public void changeState(TimerStateI state) {
		
	}

}
