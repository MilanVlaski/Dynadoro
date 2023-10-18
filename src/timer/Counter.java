package timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Counter implements ScheduledCounter {

	private final Timer timer;
	private final ScheduledExecutorService scheduler;

	private boolean isCounting = false;
	private static final int UPPER_BOUND = 14400;

	/*
	 * Makes a counter which closes after 4 hours.
	 */
	public Counter(Timer timer) {
		this.timer = timer;
		this.scheduler = Executors.newSingleThreadScheduledExecutor();
	}

	@Override
	public void countUp() {
		countDown(UPPER_BOUND);
	}

	@Override
	public void stop() {
		isCounting = false;
		scheduler.shutdown();
	}

	@Override
	public boolean isCounting() {
		return isCounting;
	}

	@Override
	public void countDown(int upperBound) {
		isCounting = true;
		
		scheduler.scheduleAtFixedRate(timer::updateDisplayedTime, 1, 1, TimeUnit.SECONDS);
		scheduler.schedule(() -> stop(), upperBound, TimeUnit.SECONDS);
	}

}
