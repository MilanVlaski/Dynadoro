package display.swing;

import java.time.LocalDateTime;

import display.Display;
import timer.Timer;

public class SwingDisplay extends Display
{

	private final MainFrame mainFrame;

	public SwingDisplay()
	{ mainFrame = new MainFrame(); }

	protected void pauseWork()
	{ mainFrame.showWorkPause(); }

	protected void pauseRest()
	{ mainFrame.showRestPause(); }

	protected void finishRest()
	{ mainFrame.showRestFinished(); }

	protected void showResting()
	{ mainFrame.showRest(); }

	protected void showWorking()
	{ mainFrame.showWorking(); }

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
