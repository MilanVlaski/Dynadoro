package timer;

public class SystemClock implements Clock {

	@Override
	public long currentTimeSeconds() {
		return System.currentTimeMillis() / 1000;
	}
	
}
