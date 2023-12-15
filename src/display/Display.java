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

	public void show(int displayedTime, DisplayState state)
	{
		show(displayedTime);
		updateState(state);
	}

	protected void updateState(DisplayState state)
	{
		switch (state)
		{
		case IDLE:
			showIdle();
			break;
		case WORKING:
			showWorking();
			break;
		case RESTING:
			showResting();
			break;
		case REST_FINISHED:
			finishRest();
			break;
		case REST_PAUSE:
			pauseRest();
			break;
		case WORK_PAUSE:
			pauseWork();
			break;
		}
	}

	public abstract void show(int displayedTime);
	public abstract void tickTime();
	public abstract void setTimer(Timer timer);

	protected abstract void showIdle();
	protected abstract void showWorking();
	protected abstract void showResting();
	protected abstract void pauseWork();
	protected abstract void pauseRest();
	protected abstract void finishRest();

}
