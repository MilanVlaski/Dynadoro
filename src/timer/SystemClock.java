package timer;

public class SystemClock implements Clock {

	private int startTime;
	
	@Override
	public int currentTimeSeconds() {
		return (int) (System.currentTimeMillis() / 1000);
	}


}
