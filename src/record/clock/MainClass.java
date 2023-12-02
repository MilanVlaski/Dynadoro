package record.clock;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import record.*;
import record.display.DayPanel;

public class MainClass
{
	public static void main(String[] args)
	{
		History history = new UsageHistory();
		ClockManager clockManager = new ClockManager(history);

		List<Day> days = clockManager.allDays();

		MainFrame mainFrame = new MainFrame();
		mainFrame.showDays(days);
	}

}

class MainFrame extends JFrame
{

	private ProductivityPanel productivityPanel = new ProductivityPanel();

	public MainFrame()
	{
		setSize(new Dimension(500, 500));

		add(productivityPanel);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void showDays(List<Day> days)
	{
		for (Day day : days)
		{
			productivityPanel.add(new DayPanel(day));
		}
	}

}
