package timer.state;

import java.time.LocalDateTime;

import record.*;
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
	public void resume(LocalDateTime now, int from)
	{ previousState.resume(now, secondsWhenPaused); }

	@Override
	public void record(UsageRecord record)
	{ record.capture(new Period(State.PAUSE, start, false)); }

}
