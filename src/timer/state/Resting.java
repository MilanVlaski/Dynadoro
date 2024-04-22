package timer.state;

import java.time.Duration;
import java.time.LocalDateTime;

import recording.History2;
import recording.State;
import timer.Timer;

public class Resting extends TimerState
{

	private final int restDuration;
	private static final int WORK_REST_RATIO = 5 / 1;

	public Resting(Timer context, LocalDateTime now, int workDuration)
	{ this(context, workDuration / WORK_REST_RATIO, now); }

	private Resting(Timer context, int restDuration, LocalDateTime now)
	{
		super(context, now);
		this.restDuration = restDuration;

		context.getDisplay().show(restDuration);
		context.getDisplay().showResting();
		context.getCounter().count(restDuration);
	}

	@Override
	public int seconds(LocalDateTime now)
	{
		int remaining = restDuration - (int) Duration.between(start, now).toSeconds();
		return Math.max(remaining, 0);
	}

	@Override
	public void begin(LocalDateTime now)
	{ context.changeState(new Working(context, now), now); }

	@Override
	public void rest(LocalDateTime now)
	{ throw new IllegalOperationException("Already taking a break."); }

	@Override
	public void pause(LocalDateTime now)
	{
		context.getDisplay().pauseRest();

		Resumable resume = (current, nowTime) -> context
		        .changeState(new Resting(context, nowTime, current), now);

		context.changeState(new Pause(context, now, resume, seconds(now)), now);
	}

	@Override
	public void resume(LocalDateTime now)
	{}

	@Override
	public void capture(History2 history2, LocalDateTime now)
	{ new SessionRecorder(history2, State.RESTING, start, now).capture(); }

}
