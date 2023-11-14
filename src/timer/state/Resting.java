package timer.state;

import java.time.Instant;

import display.Display.DisplayState;
import record.StateData;
import record.UsageRecord;
import record.StateData.State;
import timer.Timer;

public class Resting extends TimerState {

	private final int restDuration;
	private static final int WORK_REST_RATIO = 5 / 1;

	public Resting(Timer context, int now, int workDuration) {
		this(workDuration / WORK_REST_RATIO, context, now);
	}

	private Resting(int restDuration, Timer context, int now) {
		super(context, now);
		this.restDuration = restDuration;

		display.show(restDuration, DisplayState.RESTING);
		counter.count(restDuration);
	}

	@Override
	public int displayedTime(int now) {
		int remainingRestDuration = restDuration - (now - startTime);

		if (remainingRestDuration > 0)
			return remainingRestDuration;
		else
			return 0;
	}

	@Override
	public void begin(int now) {
		context.changeState(new Working(context, now));
	}

	@Override
	public void rest(int now) {
		throw new IllegalOperationException("Already taking a break.");
	}

	@Override
	public void pause(int now) {
		context.changeState(new Pause(context, this, now));
	}

	@Override
	public void resume(int now, int pauseTime) {
		int restDuration = displayedTime(pauseTime);
		context.changeState(new Resting(restDuration, context, now));
	}

	@Override
	public void record(UsageRecord record) {
		record.capture(new StateData(State.RESTING, startTime));
	}


	@Override
	public long displayedTime(Instant instant)
	{ return 0; }

	@Override
	public void begin(Instant instant)
	{}


}
