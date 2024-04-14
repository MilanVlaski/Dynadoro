package display;

import timer.Timer;

public abstract class Display
{

	public enum DisplayState
	{
		IDLE,
		WORKING,
		RESTING,
		WORK_PAUSE,
		REST_PAUSE,
		REST_FINISHED;
	}

	protected DisplayState state;
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
