package timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Counter {

	private final Timer timer;
	private final int upperBound;
	private final ScheduledExecutorService scheduler;

	private boolean isCounting = false;

	/*
	 * Makes a counter which closes after 4 hours.
	 */
	public Counter(Timer timer) {
		this(timer, 14400);
	}

	/*
	 * Makes a counter which closes after a specified number of seconds.
	 */
	public Counter(Timer timer, int upperBound) {
		this.timer = timer;
		this.upperBound = upperBound;
		this.scheduler = Executors.newSingleThreadScheduledExecutor();
	}

	public void start() {
		isCounting = true;
		
		scheduler.scheduleAtFixedRate(timer::updateDisplayedTime, 1, 1, TimeUnit.SECONDS);
		scheduler.schedule(() -> stop(), upperBound, TimeUnit.SECONDS);
	}

	public void stop() {
		isCounting = false;
		scheduler.shutdown();
	}

	public boolean isCounting() {
		return isCounting;
	}

}
