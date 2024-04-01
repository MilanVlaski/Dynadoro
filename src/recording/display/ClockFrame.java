package recording.display;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.*;

public class ClockFrame extends JFrame
{

	public ClockFrame(String title, ImageIcon clockIcon, Point position, int size)
	{
		super(title);

		Image scaledClockImage = clockIcon.getImage().getScaledInstance(size, size,
		        Image.SCALE_SMOOTH);
		JLabel clock = new JLabel(new ImageIcon(scaledClockImage));

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setLocation((int) (position.getX()), (int) position.getY());
		add(clock);
		pack();

		addWindowFocusListener(new WindowFocusListener()
		{

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
