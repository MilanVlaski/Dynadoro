package recording.display;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import recording.Day;

public class ProductivityFrame extends JFrame
{
	private ProductivityPanel productivityPanel = new ProductivityPanel();

	public ProductivityFrame()
	{
		setSize(new Dimension(935, 900));
		setTitle("History of work");

		JScrollPane scrollPane = new JScrollPane(productivityPanel);
		scrollPane.setHorizontalScrollBarPolicy(
		        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
			JLabel noneFoundLabel = new JLabel("No study sessions exist.");
			noneFoundLabel.setFont(DayPanel.loto16);
			productivityPanel.add(noneFoundLabel);
		}
		else
			for (Day day : days)
			{
				productivityPanel.add(new DayPanel(day));
			}
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

		JScrollBar systemVBar = new JScrollBar(Adjustable.VERTICAL);
		JScrollBar systemHBar = new JScrollBar(Adjustable.HORIZONTAL);
		int verticalIncrement = systemVBar.getUnitIncrement();
		int horizontalIncrement = systemHBar.getUnitIncrement();

		scrollpane.getVerticalScrollBar()
		        .setUnitIncrement(lineHeight * verticalIncrement);
		scrollpane.getHorizontalScrollBar()
		        .setUnitIncrement(charWidth * horizontalIncrement);
	}

	private static final long serialVersionUID = 1L;
}
