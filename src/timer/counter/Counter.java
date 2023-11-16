package timer.counter;

import display.Display;
import timer.Timer;

public abstract class Counter
{
	protected Display display;

	public Counter(Display display)
	{ this.display = display; }

	public abstract void countUp();
	public abstract void count(int times);
	public abstract void stop();
	public abstract boolean isRunning();

}