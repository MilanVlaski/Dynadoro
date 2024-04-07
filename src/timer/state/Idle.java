package timer.state;

import java.time.LocalDateTime;

import display.Display.DisplayState;
import recording.History2;
import timer.Timer;

public class Idle extends TimerState
{

	public Idle(Timer context, LocalDateTime now)
	{
		super(context, now);
		context.getDisplay().show(0, DisplayState.IDLE);
		context.getCounter().stop();
	}

	@Override
	public int seconds(LocalDateTime time)
	{ return 0; }

	@Override
	public void begin(LocalDateTime now)
	{ context.changeState(new Working(context, now), now); }
	@Override
	public void rest(LocalDateTime now)
	{ throw new IllegalOperationException("Can't take break if haven't started work."); }

	@Override
	public void pause(LocalDateTime now)
	{ throw new IllegalOperationException("Can't pause while doing nothing."); }

	@Override
	public void resume(LocalDateTime now)
	{ throw new IllegalOperationException("Can't resume while idle."); }

	@Override
	public void capture(History2 history2, LocalDateTime now)
	{ 
	 }


}
