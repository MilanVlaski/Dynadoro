package timer.state;

import java.time.LocalDateTime;

import record.*;
import timer.Timer;

public class Pause extends TimerState
{

	private final int secondsWhenPaused;
	private final Resumable resumable;

	public Pause(Timer context, LocalDateTime now, Resumable resumable,
	             int secondsWhenPaused)
	{
		super(context, now);
		this.resumable = resumable;
		this.secondsWhenPaused = secondsWhenPaused;

		counter.stop();
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
	public void resume(LocalDateTime now)
	{ resumable.resume(now, secondsWhenPaused); }

	@Override
	public void record(UsageRecord record)
	{ record.capture(new Period(State.PAUSE, start, false)); }

}
