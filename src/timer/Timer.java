package timer;

import display.Display;

public class Timer {

	private final Clock clock;
	private final Display display;
	
	private int startTime;
	private boolean working = false;
	
	public Timer(Clock clock, Display display) {
		this.clock = clock;
		this.display = display;
	}

	public int time() {
		return clock.currentTimeSeconds() - startTime;
	}

	public void begin() {
		startTime = clock.currentTimeSeconds();
		showTimeTicking();
		working = true;
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
