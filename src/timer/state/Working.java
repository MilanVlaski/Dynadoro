package timer.state;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;

import display.Display.DisplayState;
import record.StateData;
import record.StateData.State;
import record.UsageRecord;
import timer.Timer;

public class Working extends TimerState
{

	private final int startFrom;

	public Working(Timer context, int now)
	{ this(context, now, 0); }

	private Working(Timer context, int now, int startFrom)
	{
		super(context, now);
		this.startFrom = startFrom;

		display.show(startFrom, DisplayState.WORKING);
		counter.countUp();
	}

	public Working(Timer context, Instant current)
	{
		super(context, 0);
		startFrom = 0;
		start = current;
	}

	@Override
	public int displayedTime(int now)
	{ return startFrom + now - startTime; }

	@Override
	public void begin(int now)
	{ throw new IllegalOperationException("Timer is already running."); }

	@Override
	public void rest(int now)
	{
		int workDuration = displayedTime(now);
		context.changeState(new Resting(context, now, workDuration));
	}

	@Override
	public void pause(int now)
	{ context.changeState(new Pause(context, this, now)); }

	@Override
	public void resume(int now, int pauseTime)
	{
		int startFrom = displayedTime(pauseTime);
		context.changeState(new Working(context, now, startFrom));
	}

	@Override
	public void record(UsageRecord record)
	{ record.capture(new StateData(State.WORKING, startTime)); }

	@Override
	public long displayedTime(Instant now)
	{ return Duration.between(start, now).toSeconds(); }

	@Override
	public void begin(Instant instant)
	{}

}
