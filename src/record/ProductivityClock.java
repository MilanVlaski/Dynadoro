package record;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductivityClock
{

	private final Path path;

	public ProductivityClock(Path path)
	{ this.path = path; }

	public LocalDate date()
	{
		String filename = stripExtension(path.getFileName().toString());
		return LocalDate.parse(filename, DateTimeFormatter.ofPattern("dd_M_yyyy"));
	}

	private String stripExtension(String string)
	{ return string.replace(".*", ""); }

}
