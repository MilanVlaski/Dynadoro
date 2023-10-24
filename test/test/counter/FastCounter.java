package test.counter;

import timer.Timer;
import timer.counter.ScheduledCounter;
import timer.counter.Counter;

public class FastCounter implements Counter {

	private final ScheduledCounter scheduledCounter = new ScheduledCounter();

	public static final int DURATION_MILLISECONDS = 100;
	
	@Override
	public void countUp() {
		count(ScheduledCounter.FOUR_HOURS_IN_SECONDS);
	}

	@Override
	public void count(int upperBound) {
		scheduledCounter.count(upperBound, DURATION_MILLISECONDS);
	}

	@Override
	public void stop() {
		scheduledCounter.stop();
	}

	@Override
	public boolean isRunning() {
		return scheduledCounter.isRunning();
	}

	@Override
	public void setTimer(Timer timer) {
		scheduledCounter.setTimer(timer);
	}

}
