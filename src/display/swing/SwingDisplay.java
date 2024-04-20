package display.swing;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import display.Display;
import timer.Timer;

@Component
public class SwingDisplay extends Display
{

	private final MainFrame mainFrame;

	public SwingDisplay()
	{ mainFrame = new MainFrame(); }

	@Override
	public void pauseWork()
	{ mainFrame.showWorkPause(); }

	@Override
	public void pauseRest()
	{ mainFrame.showRestPause(); }

	@Override
	public void finishRest()
	{ mainFrame.showRestFinished(); }

	@Override
	public void showResting()
	{ mainFrame.showRest(); }

	@Override
	public void showWorking()
	{ mainFrame.showWorking(); }

	@Override
	public void showIdle()
	{ mainFrame.showIdle(); }

	@Autowired
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
