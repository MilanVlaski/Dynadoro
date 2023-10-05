package timer.state;

import timer.Timer;

public class TakingBreak extends TimerStateI{

	private final int startTime;
	private final int breakTime;
	private static final int BREAK_TIME_FACTOR = 5;

	public TakingBreak(Timer context, int startTime, int elapsedTime) {
		super(context);
		this.startTime = startTime;
		this.breakTime = elapsedTime / BREAK_TIME_FACTOR;
	}

	@Override
	public int displayedTime(int when) {
		int breakRemaining = breakTime;/* - (when - startTime);*/
//		if (breakRemaining > 0)
			return breakRemaining;
//		else
//			return 0;
	}

	@Override
	public void begin(int when) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeBreak(int when) {
		// TODO Auto-generated method stub
		
	}

}
