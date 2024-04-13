package recording.gson;

import java.io.File;
import java.io.IOException;
import java.time.*;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import recording.Period;
import recording.State;

public class JacksonPlaying
{
	public static void main(String[] args)
	{
		ObjectMapper mapper = new ObjectMapper()
//		        .registerModule(new ParameterNamesModule())
//		        .registerModule(new Jdk8Module())
//		        .registerModule(new JavaTimeModule())
				.findAndRegisterModules()
		;

//		var days = GsonPlaying.days();

		LocalDateTime now = LocalDateTime.now();
		Period period = new Period(State.WORKING, now.toLocalDate(), now.toLocalTime(),
		        now.plusMinutes(20).toLocalTime());

		write(mapper, new Period1(period));
	}

	private static void write(ObjectMapper mapper, Object obj)
	{
		try
		{
			mapper.writeValue(new File("./testJackson.json"), obj);
		} catch (StreamWriteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static class Period1
	{
		private final LocalTime start;
		private final LocalTime end;
		private final LocalDate date;

		public Period1(Period period)
		{
			this.start = period.startTime();
			this.end = period.endTime();
			this.date = period.date();
		}

		public LocalTime getStart()
		{ return start; }

		public LocalTime getEnd()
		{ return end; }

		public LocalDate getDate()
		{ return date; }

	}
}
