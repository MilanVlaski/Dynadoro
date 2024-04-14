package recording.gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.ArrayList;

import com.google.gson.GsonBuilder;

import recording.Day;
import recording.Session;
import recording.State;

public class GsonPlaying
{
	public static void main(String[] args)
	{
		var gson = new GsonBuilder().setPrettyPrinting()
		                            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
		                            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
		                            .create();

		var days = days(1);

		var json = gson.toJson(days);

		write(json, Path.of("./test.json"));

		ArrayList<Day> parsedDays = gson.fromJson(readFile(Path.of("./test.json")), ArrayList.class);

		System.out.println(parsedDays);
		System.out.println("Days serialized is same as deserialized - "
		        + (days.equals(parsedDays) ? "Yes" : "No"));

	}

	public static ArrayList<Day> days(int howMany)
	{
		LocalDateTime now = LocalDateTime.now();
		Session session = new Session(State.WORKING, now.toLocalDate(), now.toLocalTime(),
		        now.plusMinutes(20).toLocalTime());

		var periods = new ArrayList<Session>(20);
		for (int i = 0; i < 20; i++)
			periods.add(session);

		Day day = new Day(periods);

		var days = new ArrayList<Day>(5);
		for (int i = 0; i < howMany; i++)
			days.add(day);
		return days;
	}

	public static void write(String text, Path periodsFile)
	{
		try
		{
			if (Files.notExists(periodsFile))
			{
				Files.createDirectories(periodsFile.getParent());
				Files.createFile(periodsFile);
			}

			BufferedWriter writer = Files.newBufferedWriter(periodsFile);

			writer.write(text);
			writer.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String readFile(Path periodsFile)
	{
		if (Files.notExists(periodsFile))
			return "";
		else
		{
			try
			{
				return Files.readString(periodsFile);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return "";
	}
}