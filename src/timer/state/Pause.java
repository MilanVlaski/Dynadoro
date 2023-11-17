package timer.state;

import java.time.LocalDateTime;

import display.Display.DisplayState;
import record.StateData;
import record.UsageRecord;
import timer.Timer;

public class Pause extends TimerState
{

	private final TimerState previousState;

	public Pause(Timer context, TimerState previousState, LocalDateTime now)
	{
		super(context, now);
		this.previousState = previousState;

		sendDataToDisplay(previousState, now);
		counter.stop();
	}

	private void sendDataToDisplay(TimerState previousState, LocalDateTime now)
	{
		DisplayState displayState = null;
		if (previousState instanceof Working)
			displayState = DisplayState.WORK_PAUSE;
		else if (previousState instanceof Resting)
			displayState = DisplayState.REST_PAUSE;

		display.show(seconds(now), displayState);
	}

	@Override
	public int seconds(LocalDateTime now)
	{ return previousState.seconds(start); }

	@Override
	public void begin(LocalDateTime now)
	{}

	@Override
	public void rest(LocalDateTime now)
	{ context.changeState(new Resting(context, now, previousState.seconds(start))); }

	@Override
	public void pause(LocalDateTime now)
	{}

	@Override
	public void resume(LocalDateTime now, LocalDateTime pauseTime_DONT_USE)
	{ previousState.resume(now, start); }

	@Override
	public void record(UsageRecord record)
	{ record.capture(new StateData("Pause", start, false)); }

}
