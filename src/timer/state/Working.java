package timer.state;

import java.time.Duration;
import java.time.LocalDateTime;

import display.Display.DisplayState;
import record.*;
import timer.Timer;

public class Working extends TimerState
{

	private final int offset;

	public Working(Timer context, LocalDateTime now)
	{ this(context, now, 0); }

	private Working(Timer context, LocalDateTime now, int offset)
	{
		super(context, now);
		this.offset = offset;

		display.show(offset, DisplayState.WORKING);
		counter.countUp();
	}

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
	{
		display.show(seconds(now), DisplayState.WORK_PAUSE);
		context.changeState(new Pause(context, this, now, new Resumable()
		{
			@Override
			public void resume(LocalDateTime now, int from)
			{ context.changeState(new Working(context, now, from)); }
		}));
	}

	@Override
	public void resume(LocalDateTime now, int from)
	{ context.changeState(new Working(context, now, from)); }

	@Override
	public void record(UsageRecord record)
	{ record.capture(new Period(State.WORKING, start, true)); }

}
