package timer.state;

import java.time.Instant;
import java.time.LocalDateTime;

import display.Display.DisplayState;
import record.StateData;
import record.UsageRecord;
import record.StateData.State;
import timer.Timer;

public class Pause extends TimerState {

	private final TimerState previousState;

	public Pause(Timer context, TimerState previousState, int now) {
		super(context, now);
		this.previousState = previousState;

		sendDataToDisplay(previousState, now);
		counter.stop();
	}

	private void sendDataToDisplay(TimerState previousState, int now) {
		DisplayState displayState;
		if (previousState instanceof Working)
			displayState = DisplayState.WORK_PAUSE;
		else
			displayState = DisplayState.REST_PAUSE;

		display.show(displayedTime(now), displayState);
	}

	@Override
	public int displayedTime(int now) {
		return previousState.displayedTime(startTime);
	}

	@Override
	public void begin(int now) {
	}

	@Override
	public void rest(int now) {
		context.changeState(new Resting(context, now, previousState.displayedTime(startTime)));
	}

	@Override
	public void pause(int now) {
	}

	@Override
	public void resume(int now, int pauseTime) {
		previousState.resume(now, startTime);
	}

	@Override
	public void record(UsageRecord record) {
		record.capture(new StateData(State.PAUSE, startTime));
	}



	@Override
	public long displayedTime(LocalDateTime time)
	{ return 0; }

	@Override
	public void begin(LocalDateTime time)
	{}

}
