package test;

import timer.Timer;
import timer.counter.Counter;
import timer.counter.ScheduledCounter;

public class FastCounter implements ScheduledCounter {

	private final Counter counter = new Counter();
	
	@Override
	public void countUp() {
		count(Counter.UPPER_BOUND);
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
	public void count(int upperBound) {	
		counter.countDown(upperBound, 100);
	}

	@Override
	public void setTimer(Timer timer) {
		counter.setTimer(timer);
	}

}
