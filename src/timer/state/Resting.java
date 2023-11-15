package timer.state;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import display.Display.DisplayState;
import record.StateData;
import record.UsageRecord;
import record.StateData.State;
import timer.Timer;

public class Resting extends TimerState
{

	private final int restDuration;
	private static final int WORK_REST_RATIO = 5 / 1;

	public Resting(Timer context, int now, int workDuration)
	{ this(workDuration / WORK_REST_RATIO, context, now); }

	private Resting(int restDuration, Timer context, int now)
	{
		super(context, now);
		this.restDuration = restDuration;

		display.show(restDuration, DisplayState.RESTING);
		counter.count(restDuration);
	}

	public Resting(Timer context, LocalDateTime now, int workDuration)
	{
		super(context, now);
		this.restDuration = workDuration / WORK_REST_RATIO;
	}

	@Override
	public int displayedTime(int now)
	{
		long remainingRestDuration = restDuration - (now - startTime);

		if (remainingRestDuration > 0)
			return (int) remainingRestDuration;
		else
			return 0;
	}

	@Override
	public void begin(int now)
	{ context.changeState(new Working(context, now)); }

	@Override
	public void rest(int now)
	{ throw new IllegalOperationException("Already taking a break."); }

	@Override
	public void pause(int now)
	{ context.changeState(new Pause(context, this, now)); }

	@Override
	public void resume(int now, int pauseTime)
	{
		int restDuration = displayedTime(pauseTime);
		context.changeState(new Resting(restDuration, context, now));
	}

	@Override
	public void record(UsageRecord record)
	{ record.capture(new StateData(State.RESTING, startTime)); }

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
	{}

}
