package display.swing;

import java.time.LocalDateTime;

import display.Display;
import timer.Timer;

public class SwingDisplay extends Display
{

	private final MainFrame mainFrame;

	public SwingDisplay()
	{ mainFrame = new MainFrame(); }

	@Override
	protected void pauseWork()
	{ mainFrame.showWorkPause(); }

	@Override
	protected void pauseRest()
	{ mainFrame.showRestPause(); }

	@Override
	protected void finishRest()
	{ mainFrame.showRestFinished(); }

	@Override
	protected void showResting()
	{ mainFrame.showRest(); }

	@Override
	protected void showWorking()
	{ mainFrame.showWorking(); }

	@Override
	protected void showIdle()
	{ mainFrame.showIdle(); }

	@Override
	public void setTimer(Timer timer)
	{
		mainFrame.timer = timer;
		this.timer = timer;
	}

	@Override
	public void tickTime()
	{ mainFrame.setTime(timer.seconds(LocalDateTime.now())); }

	@Override
	public void show(int displayedTime)
	{ mainFrame.setTime(displayedTime); }

}
