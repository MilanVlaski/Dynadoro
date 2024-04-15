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
		setSize(new Dimension(900, 900));
		setTitle("History of work");

		JScrollPane scrollPane = new JScrollPane(productivityPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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

	public static void main(String[] args)
	{
		var days = DayPanel.testDays();

		SwingUtilities.invokeLater(() ->
		{
			ProductivityFrame productivityFrame = new ProductivityFrame();
			productivityFrame.showDays(days);
		});

	}

	private static final long serialVersionUID = 1L;
}
