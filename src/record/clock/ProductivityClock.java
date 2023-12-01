package record.clock;

import java.nio.file.Path;
import java.time.LocalDate;

public class ProductivityClock
{

	private final LocalDate date;

	public ProductivityClock(Path path)
	{ this.date = pathToDate(path); }

	public ProductivityClock(LocalDate date)
	{ this.date = date; }

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
