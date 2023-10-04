package timer;

import display.Display;

public class Timer {

	public enum TimerState {
		WORKING,
		TAKING_BREAK,
		// PAUSED,
		IDLE;
	}

	private final Clock clock;
	private final Display display;

	private int startTime;
	// perhaps the behavior changing based on enum tells us something
	private TimerState timerState = TimerState.IDLE;

	public Timer(Clock clock, Display display) {
		this.clock = clock;
		this.display = display;
	}

	public int time() {

		int elapsedTime = clock.currentTimeSeconds() - startTime;

		switch (timerState) {
		case WORKING:
			return elapsedTime;
		case TAKING_BREAK:
			return elapsedTime / 5;
		default:
			return 0;
		}
	}

	public void begin() {
		startTime = clock.currentTimeSeconds();
		showTimeTicking();
		timerState = TimerState.WORKING;
	}

	public void takeBreak() {
		timerState = TimerState.TAKING_BREAK;
	}
	
	public void display() {
		display.display(time(), timerState);
	}

	private void showTimeTicking() {
		Thread timerThread = new Thread(() -> {
			while (true) {
				display();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		timerThread.start();
	}


}
