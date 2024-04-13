package recording.gson;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.*;

public class LocalTimeTypeAdapter implements JsonSerializer<LocalTime>,
        JsonDeserializer<LocalTime>
{
	public static final String TIME_FORMAT = "HH:mm:ss";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

	@Override
	public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
	        throws JsonParseException
	{
		return LocalTime.parse(json.getAsString(), formatter);
	}

	@Override
	public JsonElement serialize(LocalTime localTime, Type typeOfSrc, JsonSerializationContext context)
	{
		return new JsonPrimitive(formatter.format(localTime));
	}
}
