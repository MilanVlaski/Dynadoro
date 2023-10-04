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
	private static final int BREAK_TIME_FACTOR = 5;

	private int startTime;
	// perhaps the behavior changing based on enum tells us something
	private TimerState timerState = TimerState.IDLE;
	private int pauseTime;
//	private TimerStateI state;
	
	public Timer(Clock clock, Display display) {
		this.clock = clock;
		this.display = display;
//		this.state = new Idle(this);
	}

	public int time() {

		int currentTime = clock.currentTimeSeconds();
		int elapsedTime = currentTime - startTime;
		
		switch (timerState) {
		case WORKING:
			return elapsedTime;
		case TAKING_BREAK:
			return elapsedTime / BREAK_TIME_FACTOR - (currentTime - pauseTime);
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
		pauseTime = clock.currentTimeSeconds();
	}
	
//	public void changeState(TimerStateI newState) {
//		this.state = newState;
//	}

	
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
		timerThread.setDaemon(true);
		timerThread.start();
	}

}
