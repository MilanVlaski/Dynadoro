package recording.gson;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.*;

public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>,
        JsonDeserializer<LocalDateTime>
{
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu HH:mm:ss");

	@Override
	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
	        throws JsonParseException
	{
		return LocalDateTime.parse(json.getAsString(), formatter);
	}

	@Override
	public JsonElement serialize(LocalDateTime localDateTime, Type typeOfSrc, JsonSerializationContext context)
	{
		return new JsonPrimitive(formatter.format(localDateTime));
	}
}