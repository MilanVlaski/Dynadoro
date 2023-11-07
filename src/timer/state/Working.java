package timer.state;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import display.Display.DisplayState;
import record.StateInfo;
import record.UsageRecord;
import timer.Timer;

public class Working extends TimerState {

	private final int startFrom;

	public Working(Timer context, int now) {
		this(context, now, 0);
	}

	private Working(Timer context, int now, int startFrom) {
		super(context, now);
		this.startFrom = startFrom;
		
		display.show(startFrom, DisplayState.WORKING);
		counter.countUp();
	}

	@Override
	public int displayedTime(int now) {
		return startFrom + now - startTime;
	}

	@Override
	public void begin(int now) {
		throw new IllegalOperationException("Timer is already running.");
	}

	@Override
	public void takeBreak(int now) {
		int workDuration = displayedTime(now);
		context.changeState(new TakingBreak(context, now, workDuration));
	}

	@Override
	public void pause(int now) {
		context.changeState(new Pause(context, this, now));
	}

	@Override
	public void resume(int now, int pauseTime) {
		int startFrom = displayedTime(pauseTime);
		context.changeState(new Working(context, now, startFrom));
	}

	@Override
	public void info(UsageRecord record) {
		record.capture(new StateInfo("Working", startTime));
	}


}
