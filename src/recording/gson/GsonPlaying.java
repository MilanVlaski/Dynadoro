package recording.gson;

import java.time.LocalDateTime;

import com.google.gson.GsonBuilder;

import recording.Period;
import recording.State;

public class GsonPlaying
{
	public static void main(String[] args)
	{
		var gson = new GsonBuilder();
		gson.setPrettyPrinting();
		gson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());

		LocalDateTime now = LocalDateTime.now();
		var json = gson.create().toJson(new Period(State.WORKING, now.toLocalDate(),
				now.toLocalTime(), now.plusMinutes(20).toLocalTime()));

		System.out.println(json);
	}
}
