package record.display;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import display.ConsoleDisplay;
import record.Day;

public class DayPanel extends JPanel
{
	private static DateTimeFormatter dateFormat;
	private final Day day;

	public DayPanel(Day day)
	{
		this.day = day;

		setLayout(new GridBagLayout());
		setBackground(new Color(189, 228, 242));

		dateFormat = DateTimeFormatter.ofPattern("EEEE, d, LLLL, yyyy");

		JLabel timeWorked = new MyLabel("Work: " + formatTime(day.timeWorked()));
		JLabel timeRested = new MyLabel("Rest: " + formatTime(day.timeRested()));
		JLabel date = new MyLabel(dateFormat.format(day.date()));

		ImageIcon clockIcon = day.clockImage();
		Image scaledClockImage = clockIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		JLabel clock = new JLabel(new ImageIcon(scaledClockImage));

		layoutComponents(timeWorked, timeRested, date, clock);

		addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseEntered(MouseEvent e)
			{ setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); }

			@Override
			public void mousePressed(MouseEvent e)
			{
				Point panelLocationOnScreen = getLocationOnScreen();

				int xOffset = getWidth();
				int yOffset = -getHeight();

				Point clockLocation = new Point(
				        (int) (panelLocationOnScreen.getX() + xOffset),
				        (int) (panelLocationOnScreen.getY() + yOffset));

				adjustFramePosition(clockLocation, xOffset, 400);
				new ClockFrame(dateFormat.format(day.date()), clockIcon, clockLocation, 400);
			}

		});
	}

	private void adjustFramePosition(Point location, int offset, int size)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		int frameWidth = size;
		int frameHeight = size;

		// Check if the frame exceeds the right edge of the screen
		if (location.getX() + frameWidth > screenWidth)
		{
			double rightFromPanel = location.getX() - offset - frameWidth - 20;
			location.setLocation(rightFromPanel, location.getY());
		}

		// Check if the frame exceeds the top edge of the screen
		if (location.getY() < 0)
			location.setLocation(location.getX(), 0);

		// Check if the frame exceeds the bottom edge of the screen
		int taskbarThickness = 80;
		if (location.getY() + frameHeight + taskbarThickness > screenHeight)
			location.setLocation(location.getX(),
			        screenHeight - taskbarThickness - frameHeight);
	}

	private void layoutComponents(JLabel timeWorked, JLabel timeRested, JLabel date,
	                              JLabel clock)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		add(clock, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		add(timeWorked, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		add(timeRested, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 3;
		add(date, gbc);
	}

	private String formatTime(Duration duration)
	{ return ConsoleDisplay.displayedMinutes((int) duration.toMinutes()); }

	private static final long serialVersionUID = 1L;

	class MyLabel extends JLabel
	{

		public MyLabel(String string)
		{
			super(string);
			setBorder(new EmptyBorder(4, 4, 4, 4));
			setFont(new Font("Loto", Font.PLAIN, 16));
			setForeground(new Color(0, 5, 10));
		}
	}
}
