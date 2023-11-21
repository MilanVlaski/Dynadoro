package timer;

import java.time.LocalDateTime;

import display.Display;
import record.UsageRecord;
import timer.counter.Counter;
import timer.state.Idle;
import timer.state.TimerState;

public class Timer
{

	private final Display display;
	private final Counter counter;

	private TimerState state;
	private UsageRecord record;

	public Timer(Display display, Counter counter, LocalDateTime now)
	{
		this.display = display;
		this.counter = counter;

		state = new Idle(this, now);
	}

	public void changeState(TimerState newState)
	{
		if (record != null)
			newState.record(record);

		this.state = newState;
	}

	public void startRecording(UsageRecord record)
	{ this.record = record; }

	public Display getDisplay()
	{ return display; }

	public Counter getCounter()
	{ return counter; }

	public int seconds(LocalDateTime now)
	{ return state.seconds(now); }

	public void begin(LocalDateTime now)
	{ state.begin(now); }

	public void rest(LocalDateTime now)
	{ state.rest(now); }

	public void pause(LocalDateTime now)
	{ state.pause(now); }

	public void resume(LocalDateTime now)
	{ state.resume(now, 0); }

	public void reset(LocalDateTime now)
	{ changeState(new Idle(this, now)); }

}
