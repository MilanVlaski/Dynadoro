package timer;

public class SystemClock implements Clock {

	@Override
	public int currentTimeSeconds() {
		return (int) (System.currentTimeMillis() / 1000);
	}


}
