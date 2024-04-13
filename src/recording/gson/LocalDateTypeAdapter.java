package recording.gson;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.*;

public class LocalDateTypeAdapter implements JsonSerializer<LocalDate>,
        JsonDeserializer<LocalDate>
{
	public static final String DATE_FORMAT = "d-MMM-uuuu";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

	@Override
	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
	        throws JsonParseException
	{
		return LocalDate.parse(json.getAsString(), formatter);
	}

	@Override
	public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context)
	{
		return new JsonPrimitive(formatter.format(date));
	}

}
