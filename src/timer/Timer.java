package timer;

import java.time.LocalDateTime;
import java.util.List;

import display.Display;
import recording.*;
import recording.clock.ClockManager;
import timer.counter.Counter;
import timer.state.Idle;
import timer.state.TimerState;

public class Timer
{

	private final Display display;
	private final Counter counter;
	private final History history;

	private TimerState state;
	private UsageRecord record;
	private ClockManager clockManager;

	public Timer(Display display, Counter counter, History history, LocalDateTime now, History2 jsonHistory)
	{
		this.display = display;
		this.counter = counter;
		this.history = history;
		state = new Idle(this, now);
	}

	public void changeState(TimerState newState)
	{
		if (record != null)
		{
			newState.record(record);
		}

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
	{ state.resume(now); }

	public void reset(LocalDateTime now)
	{ changeState(new Idle(this, now)); }

	public List<Day> retrieveDays()
	{
		return clockManager.allDays(
		        history.retrievePeriods(),
		        history.retrieveClocks());
	}

	public void setClockManager(ClockManager clockManager)
	{ this.clockManager = clockManager; }

	public void stopRecording(LocalDateTime now)
	{
		if (record != null)
			record.finishAndRecordCurrentNow(now);
	}

}
