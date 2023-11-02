package display;

import display.swing.MainFrame;

public class SwingDisplay extends Display {

	@Override
	protected void update(int displayedTime, DisplayState state) {
		new MainFrame();
	}

}
