package display;

import display.swing.MainFrame;

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
		case BREAK_FINISHED:
			break;
		case BREAK_PAUSE:
			break;
		case TAKING_BREAK:
			break;
		case WORKING:
			break;
		case WORK_PAUSE:
			break;
		default:
			break;

		}
	}

}
