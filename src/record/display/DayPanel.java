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
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(200, 200));

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE, d, LLLL, yyyy");

		JLabel timeWorked = new JLabel("Work: " + formatTime(day.timeWorked()));
		JLabel timeRested = new JLabel("Rest: " + formatTime(day.timeRested()));
		JLabel date = new JLabel(dateFormat.format(day.date()));

		ImageIcon clockIcon = day.clockImage();
		Image scaledClockImage = clockIcon.getImage().getScaledInstance(150, 150,
		        Image.SCALE_SMOOTH);
		JLabel clock = new JLabel(new ImageIcon(scaledClockImage));

		add(clock);
		add(timeWorked);
		add(timeRested);
		add(date);

		setBackground(Color.green);
	}

	private String formatTime(Duration duration)
	{ return ConsoleDisplay.displayedTime((int) duration.toSeconds()); }

}
