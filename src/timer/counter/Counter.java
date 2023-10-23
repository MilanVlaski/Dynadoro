package timer.counter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import timer.Timer;

public class Counter implements ScheduledCounter {

	private Timer timer;
	private ScheduledExecutorService scheduler;

	// four hours, in milliseconds
	public static final int UPPER_BOUND = 14400;

	public Counter() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
	}

	@Override
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	@Override
	public void countUp() {
		count(UPPER_BOUND);
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
	public void count(int times) {
		countDown(times, 1000);
	}
	
	public void countDown(int times, int duration) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		
		scheduler.scheduleAtFixedRate(timer::showTime, duration, duration, TimeUnit.MILLISECONDS);
		scheduler.schedule(() -> stop(), times * duration, TimeUnit.MILLISECONDS);
	}

}
