package timer;

import java.time.Instant;
import java.time.LocalDateTime;

import display.Display;
import display.Display.DisplayState;
import record.UsageRecord;
import sound.SoundPlayer;
import timer.counter.Counter;
import timer.state.Idle;
import timer.state.TimerState;import timer.state.Working;

public class Timer
{

	private final Clock clock;
	private final Display display;
	private final Counter counter;

	private TimerState state;
	private UsageRecord record;

	public Timer(Clock clock, Display display, Counter counter)
	{
		this.clock = clock;
		this.display = display;
		this.counter = counter;

		// this can be moved to an initialize method
		counter.setTimer(this);
		state = new Idle(this, clock.currentTimeSeconds());
	}

	public int displayedTime()
	{ return state.displayedTime(clock.currentTimeSeconds()); }

	public void begin()
	{ state.begin(clock.currentTimeSeconds()); }

	public void pause()
	{ state.pause(clock.currentTimeSeconds()); }

	public void rest()
	{ state.rest(clock.currentTimeSeconds()); }

	public void reset()
	{ changeState(new Idle(this, clock.currentTimeSeconds())); }

	public void changeState(TimerState newState)
	{
		if (record != null)
			newState.record(record);

		this.state = newState;
	}

	public void showTime()
	{ display.show(displayedTime()); }

	public void resume()
	{
		int now = clock.currentTimeSeconds();
		state.resume(now, now);
	}

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
	{ state.pause(now);; }

	public void resume(LocalDateTime now)
	{ state.resume(now, now);; }

	public void reset(LocalDateTime now)
	{changeState(new Idle(this, now));}

}
