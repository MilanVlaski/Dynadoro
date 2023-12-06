package record.clock;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import record.*;
import record.display.DayPanel;

public class MainClass
{
	public static void main(String[] args)
	{
		History history = new UsageHistory();
		ClockFileMaker fileMaker = new ClockFileMaker();
		ClockManager clockManager = new ClockManager(history, fileMaker);

		List<Day> days = clockManager.allDays();

		SwingUtilities.invokeLater(() ->
		{
			MainFrame mainFrame = new MainFrame();
			mainFrame.showDays(days);
		});

	}

}

class MainFrame extends JFrame
{

	private ProductivityPanel productivityPanel = new ProductivityPanel();

	public MainFrame()
	{
		setSize(new Dimension(800, 900));

		JScrollPane scrollPane = new JScrollPane(productivityPanel);

		add(scrollPane);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void showDays(List<Day> days)
	{
		reset();
		for (Day day : days)
			productivityPanel.add(new DayPanel(day));
	}

	private void reset()
	{
		productivityPanel.removeAll();
		productivityPanel.revalidate();
		productivityPanel.repaint();
	}

	private static final long serialVersionUID = 1L;
}
