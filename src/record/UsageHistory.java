package record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsageHistory implements History
{

	private static final String APP_NAME = "Dynadoro";
	private static final String userHome = System.getProperty("user.home");
	private static final Path path = Paths.get(userHome, APP_NAME, "sessions.txt");

	@Override
	public String read()
	{
		if (Files.notExists(path))
			return "";
		else
			try
			{
				return Files.readString(path);

			} catch (IOException e)
			{
				e.printStackTrace();
			}
		return "";
	}

	@Override
	public void write(String text)
	{
		try (BufferedWriter writer = Files.newBufferedWriter(path,
		        StandardOpenOption.APPEND))
		{
			if (Files.notExists(path))
			{
				Files.createDirectories(path.getParent());
				Files.createFile(path);
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
			String dateString = matcher.group(1);
			String stateString = matcher.group(3);
			String startTimeString = matcher.group(4);
			String endTimeString = matcher.group(5);

			LocalDate date = LocalDate.parse(dateString, Period.dateFormat);
			LocalTime startTime = LocalTime.parse(startTimeString, Period.hourFormat);
			LocalTime endTime = LocalTime.parse(endTimeString, Period.hourFormat);

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
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args)
	{
		// Makes a file and prints "true" if it exists
		UsageHistory history = new UsageHistory();
		history.read();
		System.out.println(Files.exists(path));
	}
}
