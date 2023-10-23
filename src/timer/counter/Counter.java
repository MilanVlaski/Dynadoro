package timer.counter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import timer.Timer;

public class Counter implements ScheduledCounter {

	private Timer timer;
	private ScheduledExecutorService scheduler;

	public static final int FOUR_HOURS_IN_SECONDS = 14400;
	public static final int DURATION_MILLISECONDS = 1000;

	public Counter() {
		scheduler = Executors.newSingleThreadScheduledExecutor();
	}

	@Override
	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	@Override
	public void countUp() {
		count(FOUR_HOURS_IN_SECONDS);
	}

	@Override
	public void count(int times) {
		count(times, DURATION_MILLISECONDS);
	}

	@Override
	public void stop() {
		scheduler.shutdown();
	}

	@Override
	public boolean isCounting() {
		return !scheduler.isShutdown();
	}

	public void count(int times, int durationMilliseconds) {
		scheduler.scheduleAtFixedRate(timer::showTime, durationMilliseconds,
				durationMilliseconds, TimeUnit.MILLISECONDS);

		scheduler.schedule(this::stop, times * durationMilliseconds,
				TimeUnit.MILLISECONDS);
	}

}
