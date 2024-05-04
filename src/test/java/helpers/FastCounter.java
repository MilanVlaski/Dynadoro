package helpers;

import display.Display;
import sound.SoundPlayer;
import timer.counter.Counter;
import timer.counter.ScheduledCounter;

public class FastCounter extends Counter {

	public FastCounter(Display display, SoundPlayer soundPlayer)
	{ super(display, soundPlayer); }

	private final ScheduledCounter scheduledCounter = new ScheduledCounter(display, soundPlayer);

	public static final int DURATION_MILLISECONDS = 100;

	@Override
	public void countUp() {
		count(ScheduledCounter.FOUR_HOURS_IN_SECONDS);
	}

	@Override
	public void count(int upperBound) {
		scheduledCounter.count(upperBound, DURATION_MILLISECONDS);
	}

	@Override
	public void stop() {
		scheduledCounter.stop();
	}

	@Override
	public boolean isRunning() {
		return scheduledCounter.isRunning();
	}

}
