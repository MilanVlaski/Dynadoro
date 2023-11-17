package timer.counter;

import display.Display;
import sound.SoundPlayer;
import timer.Timer;

public abstract class Counter
{
	protected final Display display;
	protected final SoundPlayer soundPlayer;

	public Counter(Display display, SoundPlayer soundPlayer)
	{
		this.display = display;
		this.soundPlayer = soundPlayer;
	}

	public abstract void countUp();
	public abstract void count(int times);
	public abstract void stop();
	public abstract boolean isRunning();

}