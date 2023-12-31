package timer.state;

import java.time.Duration;
import java.time.LocalDateTime;

import display.Display.DisplayState;
import record.*;
import timer.Timer;

public class Resting extends TimerState
{

	private final int restDuration;
	private static final int WORK_REST_RATIO = 5 / 1;

	public Resting(Timer context, LocalDateTime now, int workDuration)
	{ this(workDuration / WORK_REST_RATIO, context, now); }

	private Resting(int restDuration, Timer context, LocalDateTime now)
	{
		super(context, now);
		this.restDuration = restDuration;

		display.show(restDuration, DisplayState.RESTING);
		counter.count(restDuration);
	}

	@Override
	public int seconds(LocalDateTime now)
	{
		int remaining = restDuration - (int) Duration.between(start, now).toSeconds();
		return remaining > 0 ? remaining : 0;
	}

	@Override
	public void begin(LocalDateTime now)
	{ context.changeState(new Working(context, now)); }

	@Override
	public void rest(LocalDateTime now)
	{ throw new IllegalOperationException("Already taking a break."); }

	@Override
	public void pause(LocalDateTime now)
	{
		display.show(seconds(now), DisplayState.REST_PAUSE);

		Resumable resume = (current, nowTime) -> context
		        .changeState(new Resting(nowTime, context, current));

		context.changeState(new Pause(context, now, resume, seconds(now)));
	}

	@Override
	public void resume(LocalDateTime now)
	{}

	@Override
	public void record(UsageRecord record)
	{ record.capture(new Period(State.RESTING, start, true)); }

}
