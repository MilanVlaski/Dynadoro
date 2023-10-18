package timer;

public interface ScheduledCounter {

	void countUp();

	void stop();

	boolean isCounting();

	void countDown(int upperBound);

	void setTimer(Timer timer);

}