package record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.clock.ProductivityClock;

public class UsageHistory implements History
{

	private static final String userHome = System.getProperty("user.home");
	private static final String appName = "Dynadoro";
	private static final Path directory = Paths.get(userHome, appName);

	private static final Path periods = directory.resolve("periods.txt");
	public static final Path Clocks = directory.resolve("Clocks");

	@Override
	public String read()
	{
		if (Files.notExists(periods))
			return "";
		else
			try
			{
				return Files.readString(periods);

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		return "";
	}

	@Override
	public void write(String text)
	{
		try (BufferedWriter writer = Files.newBufferedWriter(periods,
		        StandardOpenOption.APPEND))
		{
			if (Files.notExists(periods))
			{
				Files.createDirectories(periods.getParent());
				Files.createFile(periods);
			}

			writer.write(text);
			writer.newLine();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<Period> retrievePeriods()
	{ return parsePeriods(read()); }

	public static List<Period> parsePeriods(String text)
	{
		List<Period> result = new ArrayList<>();
		Pattern pattern = Pattern.compile(Period.regex);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find())
		{
			// matched strings
			String dateString = matcher.group(1);
			String stateString = matcher.group(3);
			String startTimeString = matcher.group(4);
			String endTimeString = matcher.group(5);

			// parsed objects
			LocalDate date = LocalDate.parse(dateString, Period.dateFormat);
			LocalTime startTime = LocalTime.parse(startTimeString, Period.hourFormat);
			LocalTime endTime = LocalTime.parse(endTimeString, Period.hourFormat);

			// combining date and time into datetime
			LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
			LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

			Optional<State> state = State.of(stateString);

			if (state.isPresent())
				result.add(new Period(state.get(), startDateTime, endDateTime));
		}

		return result;
	}

	@Override
	public List<ProductivityClock> retrieveClocks()
	{
		List<ProductivityClock> clocks = new ArrayList<>();

		try (DirectoryStream<Path> stream = Files
		        .newDirectoryStream(Paths.get(Clocks.toUri())))
		{
			for (Path path : stream)
				clocks.add(new ProductivityClock(path));

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return clocks;
	}

	public static void main(String[] args)
	{
		System.out.println("periods.txt file exists: " + Files.exists(periods));
		System.out.println("Clocks folder exists: " + Files.exists(Clocks));

		UsageHistory history = new UsageHistory();
		System.out.println("Number of Clock files: " + history.retrieveClocks().size());
	}
}
