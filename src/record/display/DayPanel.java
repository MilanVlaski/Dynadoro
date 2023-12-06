package record.display;

import java.awt.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import display.ConsoleDisplay;
import record.Day;

public class DayPanel extends JPanel
{

	public DayPanel(Day day)
	{
		setPreferredSize(new Dimension(220, 220));

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, d, LLLL, yyyy");

		JLabel timeWorked = new WhiteLabel("Work: " + formatTime(day.timeWorked()));
		JLabel timeRested = new WhiteLabel("Rest: " + formatTime(day.timeRested()));
		JLabel date = new WhiteLabel(dateFormat.format(day.date()));

		ImageIcon clockIcon = day.clockImage();
		Image scaledClockImage = clockIcon.getImage().getScaledInstance(150, 150,
		        Image.SCALE_SMOOTH);
		JLabel clock = new JLabel(new ImageIcon(scaledClockImage));

		add(clock);
		add(timeWorked);
		add(timeRested);
		add(date);

		setBackground(new Color(155, 213, 235));
	}

	private String formatTime(Duration duration)
	{ return ConsoleDisplay.displayedMinutes((int) duration.toMinutes()); }

	private static final long serialVersionUID = 1L;

	class WhiteLabel extends JLabel
	{

		public WhiteLabel(String string)
		{
			super(string);
			setFont(new Font("Loto", Font.PLAIN, 15));
			setForeground(new Color(0, 5, 10));
		}
	}
}
