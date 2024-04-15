package recording;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SessionHistory implements History2
{

	public static final String userHome = System.getProperty("user.home");
	public static final String appName = "Dynadoro";
	public static final Path directory = Paths.get(userHome, appName);

	private final Path sessionsFile;

	/**
	 * Send the name of the file, and it will be used to store sessions in the user home
	 * folder, in the Dynadoro folder.
	 */
	public SessionHistory(String fileName)
	{ sessionsFile = directory.resolve(fileName + ".txt"); }

	/**
	 * For testing, it's easier to inject a file that we can delete.
	 */
	public SessionHistory(Path file)
	{ this.sessionsFile = file; }

	@Override
	public List<Day> getDays()
	{
		List<Session> sessions = getSessions();
		if (sessions.isEmpty())
		{
			return Collections.emptyList();
		}
		else
		{
			var dates = sessions.stream().map(Session::date).distinct();

			return dates.map(date -> sessionsThatMatchDate(sessions.stream(), date))
			            .map(Day::new)
			            .toList();
		}
	}

	private List<Session> sessionsThatMatchDate(Stream<Session> sessions, LocalDate date)
	{
		return sessions.filter(session -> session.date().equals(date)).toList();
	}

	@Override
	public List<Session> getSessions()
	{
		String contents = readFile(sessionsFile);
		return parsePeriods(contents);
	}

	public static List<Session> parsePeriods(String text)
	{
		List<Session> result = new ArrayList<>();
		Pattern pattern = Pattern.compile(Session.regex);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find())
		{
			// matched strings
			String dateString = matcher.group(1);
			String stateString = matcher.group(2);
			String startTimeString = matcher.group(3);
			String endTimeString = matcher.group(4);

			// parsed objects
			LocalDate date = LocalDate.parse(dateString, Session.dateFormat);
			LocalTime startTime = LocalTime.parse(startTimeString, Session.hourFormat);
			LocalTime endTime = LocalTime.parse(endTimeString, Session.hourFormat);
			State state = State.of(stateString).get();

			result.add(new Session(state, date, startTime, endTime));
		}

		return result;
	}

	@Override
	public void capture(Session session)
	{
		write(session.toString(), sessionsFile);
	}

	private static void write(String text, Path sessionsFile)
	{
		try
		{
			if (Files.notExists(sessionsFile))
			{
				Files.createDirectories(sessionsFile.getParent());
				Files.createFile(sessionsFile);
			}

			BufferedWriter writer = Files.newBufferedWriter(sessionsFile,
			                                                StandardOpenOption.APPEND);
			writer.write(text);
			writer.newLine();
			writer.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	private static String readFile(Path sessionsFile)
	{
		if (Files.notExists(sessionsFile))
			return "";
		else
			try
			{
				return Files.readString(sessionsFile);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		return "";
	}

}
