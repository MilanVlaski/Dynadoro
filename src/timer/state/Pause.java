package timer.state;

import java.time.LocalDateTime;

import display.Display.DisplayState;
import record.Period;
import record.State;
import record.UsageRecord;
import timer.Timer;

public class Pause extends TimerState
{

	private final TimerState previousState;
	private final int secondsWhenPaused;

	public Pause(Timer context, TimerState previousState, LocalDateTime now)
	{
		super(context, now);
		this.previousState = previousState;
		this.secondsWhenPaused = previousState.seconds(now);

		sendDataToDisplay(previousState);
		counter.stop();
	}

	private void sendDataToDisplay(TimerState previousState)
	{
		DisplayState displayState = (previousState instanceof Working)
		        ? DisplayState.WORK_PAUSE
		        : DisplayState.REST_PAUSE;

		display.show(secondsWhenPaused, displayState);
	}

	@Override
	public int seconds(LocalDateTime now)
	{ return secondsWhenPaused; }

	@Override
	public void begin(LocalDateTime now)
	{ context.changeState(new Working(context, now)); }

	@Override
	public void rest(LocalDateTime now)
	{ context.changeState(new Resting(context, now, secondsWhenPaused)); }

	@Override
	public void pause(LocalDateTime now)
	{}

	@Override
	public void resume(LocalDateTime now, int from)
	{ previousState.resume(now, secondsWhenPaused); }

	@Override
	public void record(UsageRecord record)
	{ record.capture(new Period(State.PAUSE, start, false)); }

}
