package record.display;

import java.awt.*;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import record.Day;

public class DayPanel extends JPanel
{

	public DayPanel(Day day)
	{
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(100, 120));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
		
		JLabel timeWorked = new JLabel("Time worked:" + formatter.format(day.timeWorked().));
		JLabel timeRested = new JLabel("Time rested:" + day.timeRested().toString());

		add(timeWorked);

		setBackground(Color.green);

	}

}
