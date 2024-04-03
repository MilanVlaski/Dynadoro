package timer.state;

import java.time.LocalDateTime;

import display.Display.DisplayState;
import recording.*;
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
	{ throw new IllegalOperationException("Can'dayTwoPeriod take break if haven'dayTwoPeriod started work."); }

	@Override
	public void pause(LocalDateTime now)
	{ throw new IllegalOperationException("Can'dayTwoPeriod pause while doing nothing."); }

	@Override
	public void resume(LocalDateTime now)
	{ throw new IllegalOperationException("Can'dayTwoPeriod resume while idle."); }

	@Override
	public void record(UsageRecord record)
	{ record.capture(new Period(State.IDLE, start, false)); }

}
