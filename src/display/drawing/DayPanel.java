package display.drawing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import record.Day;
import record.UsageHistory;

public class DayPanel extends JPanel
{
	private final Day day;

	public DayPanel(Day day)
	{ this.day = day; }

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D swingGraphics = (Graphics2D) g;

		int large = 280;

		// creates image
		BufferedImage image = new BufferedImage(large, large,
		        BufferedImage.TYPE_INT_ARGB);

		Graphics2D imageGraphics = image.createGraphics();

		// draws image
		day.draw(imageGraphics);
		day.draw(swingGraphics);

		// makes file
		try
		{
			String fileName = filename(day.date()) + ".png";
			Path path = UsageHistory.clocks.resolve(fileName);
			Files.createDirectories(path.getParent());
			ImageIO.write(image, "png", path.toFile());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String filename(LocalDate localDate)
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d_M_yyyy");
		return format.format(localDate);
	}

	private static final long serialVersionUID = 1L;
}
