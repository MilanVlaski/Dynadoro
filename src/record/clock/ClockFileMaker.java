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

public class ClockFileMaker
{

	public static final DateTimeFormatter clockFilenameFormat = DateTimeFormatter
	        .ofPattern("d_M_yyyy");

	public void makeClockFile(Day day)
	{
		String filename = filename(day.date());
		BufferedImage image = drawImage(day, 280);
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
		Path path = pathOfClockFile(fileName);
		try
		{
			Files.createDirectories(path.getParent());
			ImageIO.write(image, "png", path.toFile());

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Path pathOfClockFile(LocalDate date)
	{ return pathOfClockFile(filename(date)); }

	public static Path pathOfClockFile(String fileName)
	{ return UsageHistory.ClocksFolder.resolve(fileName); }

	public static String filename(LocalDate localDate)
	{ return clockFilenameFormat.format(localDate) + ".png"; }
}
