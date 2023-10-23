package test.counter;

import timer.Timer;
import timer.counter.Counter;
import timer.counter.ScheduledCounter;

public class FastCounter implements ScheduledCounter {

	private final Counter counter = new Counter();

	public static final int DURATION_MILLISECONDS = 100;
	
	@Override
	public void countUp() {
		count(Counter.FOUR_HOURS_IN_SECONDS);
	}

	@Override
	public void count(int upperBound) {
		counter.count(upperBound, DURATION_MILLISECONDS);
	}

	@Override
	public void stop() {
		counter.stop();
	}

	@Override
	public boolean isCounting() {
		return counter.isCounting();
	}

	@Override
	public void setTimer(Timer timer) {
		counter.setTimer(timer);
	}

}
