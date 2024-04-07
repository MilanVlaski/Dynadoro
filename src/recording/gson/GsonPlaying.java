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
		gson.registerTypeAdapter(LocalDateTime.class, args);
		
		LocalDateTime now = LocalDateTime.now();
		var json = gson.create().toJson(new Period(State.WORKING, now, now.plusMinutes(20)));
		
		System.out.println(json);
	}
}
