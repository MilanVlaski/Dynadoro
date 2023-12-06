package record.display;

import java.awt.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import display.ConsoleDisplay;
import record.Day;

public class DayPanel extends JPanel
{

	public DayPanel(Day day)
	{
		setLayout(new GridBagLayout());
		setBackground(new Color(189, 228, 242));
		setBorder(new EmptyBorder(6, 6, 6, 6));

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, d, LLLL, yyyy");

		JLabel timeWorked = new MyLabel("Work: " + formatTime(day.timeWorked()));
		JLabel timeRested = new MyLabel("Rest: " + formatTime(day.timeRested()));
		JLabel date = new MyLabel(dateFormat.format(day.date()));

		ImageIcon clockIcon = day.clockImage();
		Image scaledClockImage = clockIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		JLabel clock = new JLabel(new ImageIcon(scaledClockImage));

		layoutComponents(timeWorked, timeRested, date, clock);
	}

	private void layoutComponents(JLabel timeWorked, JLabel timeRested, JLabel date, JLabel clock)
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
