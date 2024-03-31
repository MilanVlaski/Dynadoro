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

	private final Path periodsFile;
	public static final Path ClocksFolder = directory.resolve("Clocks");

	public UsageHistory(String fileName)
	{
		periodsFile = directory.resolve(fileName + ".txt");
	}

	@Override
	public String read()
	{
		if (Files.notExists(periodsFile))
			return "";
		else
			try
			{
				return Files.readString(periodsFile);

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		return "";
	}

	@Override
	public void write(String text)
	{
		try
		{
			if (Files.notExists(periodsFile))
			{
				Files.createDirectories(periodsFile.getParent());
				Files.createFile(periodsFile);
			}

			BufferedWriter writer = Files.newBufferedWriter(periodsFile,
			        StandardOpenOption.APPEND);

			writer.write(text);
			writer.newLine();

			writer.close();

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

			// THIS IS JUST A PATCH, because we write midnight sessions as
			// 2023-11-05, Working, 23:59, 00:01. which is pretty bad
			LocalDateTime endDateTime = (endTime.compareTo(startTime) < 0)
			        ? LocalDateTime.of(date.plusDays(1), endTime)
			        : LocalDateTime.of(date, endTime);
			//

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
		        .newDirectoryStream(Paths.get(ClocksFolder.toUri())))
		{

			for (Path path : stream)
				clocks.add(new ProductivityClock(path));

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return clocks;
	}
}
