package timer;

import display.Display;

public class Timer {

	private final Clock clock;
	private final Display display;

	private int startTime;
	private boolean working = false;
	private boolean takingBreak = false;

	public Timer(Clock clock, Display display) {
		this.clock = clock;
		this.display = display;
	}

	public int time() {
		int elapsedTime = clock.currentTimeSeconds() - startTime;
		if (working)
			return elapsedTime;
		else if (takingBreak)
			return elapsedTime / 5;
		else
			return 0;
	}

	public void begin() {
		startTime = clock.currentTimeSeconds();
		showTimeTicking();
		working = true;
	}

	public void takeBreak() {
		takingBreak  = true;
		working = false;
	}

	private void showTimeTicking() {
		Thread timerThread = new Thread(() -> {
			while (true) {
				display.display(time());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		timerThread.start();
	}

	public boolean working() {
		return working;
	}

}
