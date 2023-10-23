package test;

import timer.Timer;
import timer.counter.Counter;
import timer.counter.ScheduledCounter;

public class FastCounter implements ScheduledCounter {

	private final Counter counter = new Counter();
	
	@Override
	public void countUp() {
		counter.countUp();
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
	public void countDown(int upperBound) {	
		counter.countDown(upperBound);
	}

	@Override
	public void setTimer(Timer timer) {
		counter.setTimer(timer);
	}

}
