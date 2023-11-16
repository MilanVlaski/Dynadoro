package timer.counter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import display.Display;
import display.Display.DisplayState;
import sound.SoundPlayer;
import timer.Timer;

public class ScheduledCounter extends Counter
{

	private ScheduledExecutorService scheduler;
	private boolean isRunning = false;

	public static final int FOUR_HOURS_IN_SECONDS = 14400;
	public static final int DURATION_MILLISECONDS = 1000;

	public ScheduledCounter(Display display)
	{
		super(display);
		initScheduler();
	}

	private void initScheduler()
	{ scheduler = Executors.newSingleThreadScheduledExecutor(); }

	@Override
	public void countUp()
	{ count(FOUR_HOURS_IN_SECONDS); }

	@Override
	public void count(int times)
	{ count(times, DURATION_MILLISECONDS); }

	@Override
	public void stop()
	{
		isRunning = false;
		scheduler.shutdownNow();
	}

	@Override
	public boolean isRunning()
	{ return isRunning; }

	public void count(int times, int durationMilliseconds)
	{

		if (isRunning)
			stop();

		initScheduler();

		isRunning = true;
		scheduler.scheduleAtFixedRate(display::tickTime, durationMilliseconds,
		        durationMilliseconds, TimeUnit.MILLISECONDS);

		scheduler.schedule(this::onFinish, times * durationMilliseconds,
		        TimeUnit.MILLISECONDS);
	}

	private void onFinish()
	{
		if (isRunning)
		{
			stop();
			SoundPlayer.play();
			display.show(0, DisplayState.REST_FINISHED);
		}
	}

}
