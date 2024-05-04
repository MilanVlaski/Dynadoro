package recording.display;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import recording.clock.ClockPanel;

public class ClockFrame extends JFrame
{

	public ClockFrame(String title, ClockPanel clockPanel, Point position, int width,
	                  int height)
	{
		super(title);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setLocation((int) (position.getX()), (int) position.getY());
		add(clockPanel);
		setSize(new Dimension(width, height));

		addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e)
			{ setVisible(false); }

			@Override
			public void windowGainedFocus(WindowEvent e)
			{}
		});

		setVisible(true);
	}

}
