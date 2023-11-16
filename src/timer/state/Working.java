package timer.state;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;

import display.Display.DisplayState;
import record.StateData;
import record.StateData.State;
import record.UsageRecord;
import timer.Timer;

public class Working extends TimerState
{

	private final int offset;

	public Working(Timer context, LocalDateTime now)
	{ this(context, now, 0); }

	public Working(Timer context, LocalDateTime now, int offset)
	{
		super(context, now);
		this.offset = offset;

		display.show(offset, DisplayState.WORKING);
		counter.countUp();
	}

	@Override
	public void record(UsageRecord record)
	{ record.capture(new StateData(State.WORKING, 0)); }

	@Override
	public int seconds(LocalDateTime now)
	{ return offset + (int) Duration.between(start, now).toSeconds(); }

	@Override
	public void begin(LocalDateTime time)
	{ throw new IllegalOperationException("Timer is already running."); }

	@Override
	public void rest(LocalDateTime now)
	{
		int workDuration = seconds(now);
		context.changeState(new Resting(context, now, workDuration));
	}

	@Override
	public void pause(LocalDateTime now)
	{ context.changeState(new Pause(context, this, now)); }

	@Override
	public void resume(LocalDateTime now, LocalDateTime pauseTime)
	{
		int startFrom = seconds(pauseTime);
		context.changeState(new Working(context, now, startFrom));
	}

}
