package recording.clock;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import recording.Session;
import recording.State;

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

	public static void main(String[] args)
	{
		Frame frame = new Frame();
	}

	private static class Frame extends JFrame
	{
		JPanel clockPanel;

		public Frame()
		{
			Session session = new Session(State.WORKING, LocalDate.now(), LocalTime.now(),
			                              LocalTime.now().plusHours(3));
			this.clockPanel = new ClockPanel(List.of(session));

			add(clockPanel);
			pack();
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setVisible(true);
		}


	}
}
