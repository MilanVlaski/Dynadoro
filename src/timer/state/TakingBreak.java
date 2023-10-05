package timer.state;

import timer.Timer;

public class TakingBreak extends TimerStateI{

	private final int startTime;

	public TakingBreak(Timer context, int startTime) {
		super(context);
		this.startTime = startTime;
	}

	@Override
	public int time() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeBreak() {
		// TODO Auto-generated method stub
		
	}

}
