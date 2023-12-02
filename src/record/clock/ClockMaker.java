package record.clock;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.imageio.ImageIO;

import record.*;

public class ClockMaker
{

	public static final DateTimeFormatter clockFileFormat = DateTimeFormatter
	        .ofPattern("d_M_yyyy");

	public static void makeClockFile(Day day)
	{
		int size = 280;
		BufferedImage image = drawImage(day, size);
		String filename = filename(day.date());
		makeImageFile(filename, image);
	}

	private static BufferedImage drawImage(Day day, int size)
	{
		BufferedImage image = new BufferedImage(size, size,
		        BufferedImage.TYPE_INT_ARGB);

		Graphics2D imageGraphics = image.createGraphics();

		day.draw(imageGraphics, size);
		return image;
	}

	private static void makeImageFile(String fileName, BufferedImage image)
	{
		Path path = UsageHistory.Clocks.resolve(fileName);
		try
		{
			Files.createDirectories(path.getParent());
			ImageIO.write(image, "png", path.toFile());

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String filename(LocalDate localDate)
	{ return clockFileFormat.format(localDate) + ".png"; }

	public static void main(String[] args)
	{
		UsageHistory history = new UsageHistory();
		List<Period> periods = history.retrievePeriods();
		List<Day> days = ClockManager.createDays(periods);

		for (Day day : days)
			ClockMaker.makeClockFile(day);

		System.out.println("Drawing Clocks was attempted.");
	}
}
