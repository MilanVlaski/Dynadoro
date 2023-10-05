package timer.state;

import timer.Timer;

public class TakingBreak extends TimerStateI{

	private final int startTime;

	public TakingBreak(Timer context, int startTime) {
		super(context);
		this.startTime = startTime;
	}

	@Override
	public int displayedTime(int when) {
		// TODO Auto-generated method stub
		return 0;
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
