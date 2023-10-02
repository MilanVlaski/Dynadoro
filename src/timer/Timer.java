package timer;

public class Timer {

	private final Clock clock;
	private int startTime;
	private boolean working = false;
	
	public Timer(Clock clock) {
		this.clock = clock;
	}

	public int time() {
		return clock.currentTimeSeconds() - startTime;
	}

	public void begin() {
		startTime = clock.currentTimeSeconds();
		working = true;
	}

	public boolean working() {
		return working;
	}

}
