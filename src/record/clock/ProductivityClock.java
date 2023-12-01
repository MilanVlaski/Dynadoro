package record.clock;

import java.nio.file.Path;
import java.time.LocalDate;

import record.Day;

public class ProductivityClock
{

	private final LocalDate date;

	/**
	 * Takes an already existing path, and makes an object out of it.
	 */
	public ProductivityClock(Path path)
	{ this.date = pathToDate(path); }

	/**
	 * Creates the file based on this date.
	 */
	public ProductivityClock(Day day)
	{
		this.date = day.date();
		ClockMaker.makeClock(day);
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

	@Override
	public boolean equals(Object obj)
	{
		ProductivityClock clock = (ProductivityClock) obj;
		return clock.date.equals(date);
	}
}
