package record.clock;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import javax.swing.ImageIcon;

import record.Day;
import record.UsageHistory;

public class ProductivityClock
{

	private final LocalDate date;
	private final Path path;

	/**
	 * Takes an already existing path, and makes an object out of it.
	 */
	public ProductivityClock(Path path)
	{
		this.date = pathToDate(path);
		this.path = path;
	}

	/**
	 * Creates the file based on this date.
	 */
	public ProductivityClock(Day day)
	{
		this.date = day.date();
		this.path = dateToPath(day.date());
		ClockMaker.makeClockFile(day);
	}

	private Path dateToPath(LocalDate date)
	{
		String filename = ClockMaker.clockFileFormat.format(date) + ".png";
		return UsageHistory.Clocks.resolve(filename);
	}

	public LocalDate date()
	{ return date; }

	private LocalDate pathToDate(Path path)
	{
		String filename = stripExtension(path.getFileName().toString());
		return LocalDate.parse(filename, ClockMaker.clockFileFormat);
	}

	private String stripExtension(String string)
	{ return string.replaceAll("\\..*", ""); }

	public ImageIcon image()
	{ return getImage(path); }

	private static ImageIcon getImage(Path path)
	{
		if (Files.exists(path))
			return new ImageIcon(path.toString());
		else
		{
			System.err.println("Image file not found: " + path);
			return null;
		}
	}

	@Override
	public boolean equals(Object obj)
	{
		ProductivityClock clock = (ProductivityClock) obj;
		return clock.date.equals(date);
	}
}
