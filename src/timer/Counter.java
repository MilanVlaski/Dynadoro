package timer;

public class Counter {

	private boolean isCounting;

	public Counter(Timer timer) {
		// TODO Auto-generated constructor stub
	}

	public Counter(Timer timer, int upperBound) {
		// TODO Auto-generated constructor stub
	}

	public void start() {
		isCounting = true;
	}

	public boolean isCounting() {
		return isCounting;
	}

	public void stop() {
		isCounting = false;
	}

}
