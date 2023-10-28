package timer.counter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import sound.SoundPlayer;
import timer.Timer;

public class ScheduledCounter implements Counter {

	private Timer timer;
	private ScheduledExecutorService scheduler;
	private boolean isRunning = false;

	public static final int FOUR_HOURS_IN_SECONDS = 14400;
	public static final int DURATION_MILLISECONDS = 1000;

	public ScheduledCounter() {
		initScheduler();
	}

	private void initScheduler() {
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
		isRunning = false;
		scheduler.shutdown();
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	public void count(int times, int durationMilliseconds) {

		if (isRunning())
			stop();
		
		initScheduler();

		isRunning = true;
		scheduler.scheduleAtFixedRate(timer::showTime, durationMilliseconds,
				durationMilliseconds, TimeUnit.MILLISECONDS);

		scheduler.schedule(this::onFinish, times * durationMilliseconds,
				TimeUnit.MILLISECONDS);
	}

	private void onFinish() {
		stop();
		timer.finishBreak();
	}

}
