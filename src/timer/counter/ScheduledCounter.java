package timer.counter;

import timer.Timer;

public interface ScheduledCounter {

	void countUp();

	void stop();

	boolean isCounting();

	void count(int upperBound);

	void setTimer(Timer timer);

}