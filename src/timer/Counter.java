package timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Counter implements ScheduledCounter {

	private Timer timer;
	private ScheduledExecutorService scheduler;

	private static final int UPPER_BOUND = 14400;

	/*
	 * Makes a counter which closes after 4 hours.
	 */
	public Counter() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
	}

	@Override
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	@Override
	public void countUp() {
		countDown(UPPER_BOUND);
	}

	@Override
	public void stop() {
		scheduler.shutdown();
	}

	@Override
	public boolean isCounting() {
		return !scheduler.isShutdown();
	}

	@Override
	public void countDown(int upperBound) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		
		scheduler.scheduleAtFixedRate(timer::showTime, 1, 1, TimeUnit.SECONDS);
		scheduler.schedule(() -> stop(), upperBound, TimeUnit.SECONDS);
	}

}
