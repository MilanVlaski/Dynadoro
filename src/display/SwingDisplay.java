package display;

import display.swing.MainFrame;
import timer.Timer;

public class SwingDisplay extends Display {

	private final MainFrame mainFrame;
	
	public SwingDisplay() {
		mainFrame = new MainFrame();
	}
	
	@Override
	protected void update(int displayedTime, DisplayState state) {
		
		mainFrame.setTime(displayedTime);
		
		switch (state) {
		case IDLE:
			mainFrame.showStartButton();
			break;
		case WORKING:
//			mainFrame.showPauseButton();
//			mainFrame.showBreakButton();
			break;
		case BREAK_FINISHED:
			break;
		case BREAK_PAUSE:
			break;
		case TAKING_BREAK:
			break;
		case WORK_PAUSE:
			break;
		default:
			break;

		}
	}

	@Override
	protected void update(int displayedTime) {
		mainFrame.setTime(displayedTime);
	}

	public void setModel(Timer timer) {
		mainFrame.timer = timer;
	}

}
