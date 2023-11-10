package display.swing;

import display.Display;
import timer.Timer;

public class SwingDisplay extends Display
{

	private final MainFrame mainFrame;

	public SwingDisplay()
	{ mainFrame = new MainFrame(); }

	@Override
	protected void updateTime(int displayedTime)
	{ mainFrame.setTime(displayedTime); }

	protected void pauseWork()
	{ mainFrame.showWorkPause(); }

	protected void pauseRest()
	{ mainFrame.showBreakPause(); }

	protected void finishRest()
	{ mainFrame.showBreakFinished(); }

	protected void showResting()
	{ mainFrame.showBreak(); }

	protected void showWorking()
	{ mainFrame.showWorking(); }

	protected void showIdle()
	{ mainFrame.showIdle(); }

	public void setModel(Timer timer)
	{ mainFrame.timer = timer; }

}
