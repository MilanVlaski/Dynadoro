package timer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Moment
{

	private final Instant instant;

	public Moment(LocalDateTime time)
	{
		ZoneId zone = ZoneId.of("America/New_York");
		instant = time.atZone(zone).toInstant();
	}

}
