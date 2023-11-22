package display.drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JFrame;

import record.Day;
import record.StateData;
import record.StateData.State;

public class MainClass
{

	public static void main(String[] args)
	{
		LocalDateTime zero = LocalDateTime.of(2023, 11, 18, 0, 0);
		LocalDateTime twentyFive = LocalDateTime.of(2023, 11, 18, 0, 25);
		LocalDateTime thirty = LocalDateTime.of(2023, 11, 18, 0, 30);

		Day day = new Day(List.of(new StateData(State.WORK, "Working",
		        zero,
		        twentyFive)));

		new MyFrame(day);
	}
}

class MyFrame extends JFrame
{
	public MyFrame(Day day)
	{
		DayPanel dayPanel = new DayPanel(day);
		add(dayPanel);

		setSize(new Dimension(1000, 1000));
		setBackground(new Color(255, 255, 255));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Todays clock");
		setVisible(true);
	}
}