package timer.counter;

import timer.Timer;

public interface Counter {

	void setTimer(Timer timer);
	void countUp();
	void count(int times);
	void stop();
	boolean isRunning();

}