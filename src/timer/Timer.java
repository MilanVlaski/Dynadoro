package timer;

import java.time.Instant;
import java.time.LocalDateTime;

import display.Display;
import display.Display.DisplayState;
import record.UsageRecord;
import sound.SoundPlayer;
import timer.counter.Counter;
import timer.state.Idle;
import timer.state.TimerState;
import timer.state.Working;

public class Timer
{

	private final Clock clock;
	private final Display display;
	private final Counter counter;

	private TimerState state;
	private UsageRecord record;

	public Timer(Clock clock, Display display, Counter counter, LocalDateTime now)
	{
		this.clock = clock;
		this.display = display;
		this.counter = counter;

		// this can be moved to an initialize method
		counter.setTimer(this);
		state = new Idle(this, now);
	}

	public void changeState(TimerState newState)
	{
		if (record != null)
			newState.record(record);

		this.state = newState;
	}

//	public void showTime()
//	{ display.show(displayedTime()); }

	public void finishBreak()
	{
		SoundPlayer.play();
		display.show(0, DisplayState.REST_FINISHED);
	}

	public void startRecording(UsageRecord record)
	{ this.record = record; }

	public Display getDisplay()
	{ return display; }

	public Counter getCounter()
	{ return counter; }

	public long seconds(LocalDateTime now)
	{ return state.seconds(now); }

	public void begin(LocalDateTime now)
	{ state.begin(now); }

	public void rest(LocalDateTime now)
	{ state.rest(now); }

	public void pause(LocalDateTime now)
	{ state.pause(now); }

	public void resume(LocalDateTime now)
	// last parameter is unused, but its useful internally
	{ state.resume(now, LocalDateTime.MIN); }

	public void reset(LocalDateTime now)
	{ changeState(new Idle(this, now)); }

}
