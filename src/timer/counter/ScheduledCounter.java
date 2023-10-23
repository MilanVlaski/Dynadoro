package timer.counter;

import timer.Timer;

public interface ScheduledCounter {

	void setTimer(Timer timer);
	void countUp();
	void count(int upperBound);
	void stop();
	boolean isCounting();

}