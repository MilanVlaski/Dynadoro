package record.clock;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JFrame;

import record.*;

public class MainClass
{
	public static void main(String[] args)
	{
		History history = new UsageHistory();
		ClockManager clockManager = new ClockManager(history);

		List<Day> days = clockManager.allDays();

		new MainFrame(days);
	}

}

class MainFrame extends JFrame
{

	List<Day> days;

	public MainFrame(List<Day> days) throws HeadlessException
	{
		this.days = days;

		setSize(new Dimension(500, 500));

		setLocationRelativeTo(null);
		setVisible(true);
	}

}
