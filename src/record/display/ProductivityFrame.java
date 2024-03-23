package record.display;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.List;

import javax.swing.*;

import record.*;
import record.clock.ClockFileMaker;
import record.clock.ClockManager;

public class ProductivityFrame extends JFrame
{
	private ProductivityPanel productivityPanel = new ProductivityPanel();

	public ProductivityFrame()
	{
		setSize(new Dimension(850, 900));
		setTitle("History of work");

		JScrollPane scrollPane = new JScrollPane(productivityPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fixScrolling(scrollPane);

		add(scrollPane);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void showDays(List<Day> days)
	{
		reset();
		if (days.isEmpty())
		{
			productivityPanel.add(new JLabel("No study sessions exist."));
			return;
		}
		for (Day day : days)
			productivityPanel.add(new DayPanel(day));
	}

	private void reset()
	{
		productivityPanel.removeAll();
		productivityPanel.revalidate();
		productivityPanel.repaint();
	}

	public static void fixScrolling(JScrollPane scrollpane)
	{
		JLabel systemLabel = new JLabel();
		FontMetrics metrics = systemLabel.getFontMetrics(systemLabel.getFont());
		int lineHeight = metrics.getHeight();
		int charWidth = metrics.getMaxAdvance();

		JScrollBar systemVBar = new JScrollBar(JScrollBar.VERTICAL);
		JScrollBar systemHBar = new JScrollBar(JScrollBar.HORIZONTAL);
		int verticalIncrement = systemVBar.getUnitIncrement();
		int horizontalIncrement = systemHBar.getUnitIncrement();

		scrollpane.getVerticalScrollBar().setUnitIncrement(lineHeight * verticalIncrement);
		scrollpane.getHorizontalScrollBar().setUnitIncrement(charWidth * horizontalIncrement);
	}

	public static void main(String[] args)
	{
		History history = new UsageHistory("periods");
		ClockFileMaker fileMaker = new ClockFileMaker();
		ClockManager clockManager = new ClockManager(history, fileMaker);

		List<Day> days = clockManager.allDays();

		SwingUtilities.invokeLater(() ->
		{
			ProductivityFrame productivityFrame = new ProductivityFrame();
			productivityFrame.showDays(days);
		});

	}

	private static final long serialVersionUID = 1L;
}
