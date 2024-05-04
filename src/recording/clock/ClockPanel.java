package recording.clock;

import java.awt.*;
import java.util.List;

import javax.swing.JPanel;

import recording.Session;

public class ClockPanel extends JPanel
{

	private final List<Session> sessions;

	public ClockPanel(List<Session> sessions)
	{
		this.sessions = sessions;
		setPreferredSize(new Dimension(190, 190));
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		ClockDrawer.draw((Graphics2D) g, sessions, getWidth());
	}

}
