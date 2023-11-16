package display;

import java.time.LocalDateTime;

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

	protected int time;
	protected DisplayState state;
	protected Timer timer;

	public void show(int displayedTime)
	{
		this.time = displayedTime;
		updateTime(displayedTime);
	}

	public void show(int displayedTime, DisplayState state)
	{
		this.state = state;
		this.time = displayedTime;
		updateTimeAndState(displayedTime, state);
	}

	protected void updateTimeAndState(int displayedTime, DisplayState state)
	{
		updateTime(displayedTime);
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

	protected abstract void updateTime(int displayedTime);
	protected abstract void pauseWork();
	protected abstract void pauseRest();
	protected abstract void finishRest();
	protected abstract void showResting();
	protected abstract void showWorking();
	protected abstract void showIdle();

	public void tickTime()
	{}

}
