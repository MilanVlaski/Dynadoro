package timer;

public class Timer {

	private final Clock clock;
	private int startTime;
	
	public Timer(Clock clock) {
		this.clock = clock;
	}

	public double time() {
		return 0;
	}

	public void begin() {
		startTime = clock.currentTimeSeconds();
	}

	public int end() {
		return clock.currentTimeSeconds() - startTime;
	}
}
