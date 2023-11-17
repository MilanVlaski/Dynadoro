package timer.state;

import java.time.Instant;
import java.time.LocalDateTime;

import display.Display.DisplayState;
import record.StateData;
import record.UsageRecord;
import record.StateData.State;
import timer.Timer;

public class Idle extends TimerState
{

	public Idle(Timer context, LocalDateTime now)
	{
		super(context, now);
		display.show(0, DisplayState.IDLE);
		counter.stop();
	}

	@Override
	public int seconds(LocalDateTime time)
	{ return 0; }

	@Override
	public void begin(LocalDateTime now)
	{ context.changeState(new Working(context, now)); }

	@Override
	public void rest(LocalDateTime now)
	{ throw new IllegalOperationException("Can't take break if haven't started work."); }

	@Override
	public void pause(LocalDateTime now)
	{ throw new IllegalOperationException("Can't pause while doing nothing."); }

	@Override
	public void resume(LocalDateTime now, LocalDateTime pauseTime)
	{ throw new IllegalOperationException("Can't resume while idle."); }

	@Override
	public void record(UsageRecord record)
	{ record.capture(new StateData(State.IDLE, 0)); }

}
