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
		// TODO yuck
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
	{ context.changeState(new Working(context, now)); }

	@Override
	public void rest(LocalDateTime now)
	{
		// TODO yucky
		previousState.rest(now);
		if (previousState instanceof Working)
			context.changeState(new Resting(context, now, previousState.seconds(start)));
		else
			throw new IllegalOperationException("Can't rest if haven't worked.");
//		 Rest and Idle also exist as states. so where is the elseif for Idle?
		// well, you can never 
	}

	@Override
	public void pause(LocalDateTime now)
	{}

	@Override
	public void resume(LocalDateTime now, LocalDateTime pauseTime_DONT_USE)
	{ 
		
		previousState.resume(now, start);
	}

	@Override
	public void record(UsageRecord record)
	{ record.capture(new StateData("Pause", start, false)); }

}
