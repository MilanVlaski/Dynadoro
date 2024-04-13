package recording;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import recording.gson.LocalDateTypeAdapter;
import recording.gson.LocalTimeTypeAdapter;

public class SessionHistory implements History2
{

	public static final String userHome = System.getProperty("user.home");
	public static final String appName = "Dynadoro";
	public static final Path directory = Paths.get(userHome, appName);

	private final Gson gson = new GsonBuilder().setPrettyPrinting()
	        .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
	        .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter()).create();

	private final Path sessionsFile;

	public SessionHistory(String fileName)
	{
		sessionsFile = directory.resolve(fileName + ".json");
	}

	public SessionHistory(Path tempDir)
	{
		this.sessionsFile = tempDir;
	}

	@Override
	public List<Period> getSessions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void capture(Period period)
	{
		List<Day> days = getDays();
		if (days.isEmpty())
		{
			write(gson.toJson(new Day(List.of(period))), sessionsFile);
		}
	}

	private static void write(String json, Path sessionsFile)
	{
		try
		{
			if (Files.notExists(sessionsFile))
			{
				Files.createDirectories(sessionsFile.getParent());
				Files.createFile(sessionsFile);
			}

			BufferedWriter writer = Files.newBufferedWriter(sessionsFile);

			writer.write(json);
			writer.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<Day> getDays()
	{
		if (Files.notExists(sessionsFile))
			return Collections.emptyList();
		else
			return gson.fromJson(readFile(sessionsFile), ArrayList.class);
	}

	private static String readFile(Path sessionsFile)
	{
		if (Files.notExists(sessionsFile))
			return "";
		else
		{
			try
			{
				return Files.readString(sessionsFile);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return "";
	}

}
