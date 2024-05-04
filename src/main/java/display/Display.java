package display;

import timer.Timer;

public abstract class Display
{

	protected Timer timer;

	public abstract void show(int displayedTime);
	public abstract void tickTime();
	public abstract void setTimer(Timer timer);

	public abstract void showIdle();
	public abstract void showWorking();
	public abstract void showResting();
	public abstract void pauseWork();
	public abstract void pauseRest();
	public abstract void finishRest();

}
