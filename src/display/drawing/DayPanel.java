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

		makeClock(day);
	}

	public static void makeClock(Day day)
	{
		int large = 280;
		BufferedImage image = drawImage(day, large);
		makeImageFile(filename(day.date()), image);
	}

	public static void makeImageFile(String fileName, BufferedImage image)
	{
		Path path = UsageHistory.clocks.resolve(fileName);
		try
		{
			Files.createDirectories(path.getParent());
			ImageIO.write(image, "png", path.toFile());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static BufferedImage drawImage(Day day, int size)
	{
		BufferedImage image = new BufferedImage(size, size,
		        BufferedImage.TYPE_INT_ARGB);

		Graphics2D imageGraphics = image.createGraphics();

		day.draw(imageGraphics);
		return image;
	}

	public static String filename(LocalDate localDate)
	{
		DateTimeFormatter format = DateTimeFormatter.ofPattern("d_M_yyyy");
		return format.format(localDate) + ".png";
	}

	private static final long serialVersionUID = 1L;
}
