package timer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import display.Display;
import recording.Day;
import recording.History2;
import timer.counter.Counter;
import timer.state.Idle;
import timer.state.TimerState;

@Component
public class Timer
{

	private final Display display;
	private final Counter counter;
	private final History2 history2;

	private TimerState state;

	@Autowired
	public Timer(Display display, Counter counter, LocalDateTime now, History2 history2)
	{
		this.display = display;
		this.counter = counter;
		this.history2 = history2;
		state = new Idle(this, now);
	}

	public void changeState(TimerState newState, LocalDateTime now)
	{
		state.capture(history2, now);
		this.state = newState;
	}

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
	{ changeState(new Idle(this, now), now); }

	public List<Day> retrieveDays()
	{
		return history2.getDays();
	}

}
