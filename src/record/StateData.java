package record;

import java.awt.Graphics;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StateData
{
	public enum TrackedState
	{
		WORK,
		REST
	}

	private final String name;
	private final boolean getsRecorded;

	private final LocalDateTime start;
	private LocalDateTime end;

	public StateData(String name, LocalDateTime start, boolean getsRecorded)
	{
		this.name = name;
		this.start = start;
		this.getsRecorded = getsRecorded;
	}

	public StateData(String name, LocalDateTime start, LocalDateTime end)
	{
		this.name = name;
		this.start = start;
		this.end = end;
		this.getsRecorded = true;
	}

	@Override
	public String toString()
	{
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, EEEE");
		DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH:mm");

		String endTime = (end != null) ? hourFormat.format(end) : "unknown";

		return String.join(", ", dateFormat.format(start),
		        name, hourFormat.format(start), endTime);
	}

	public LocalDateTime startTime()
	{ return start; }

	public boolean shouldBeRecorded()
	{ return getsRecorded; }

	public void finish(LocalDateTime end)
	{ this.end = end; }

	public Duration duration()
	{
		if (end != null)
			return Duration.between(start, end);
		else
			return Duration.ZERO;

	}

	public TrackedState type()
	{
		if (name == "Working")
			return TrackedState.WORK;
		else if (name == "Resting")
			return TrackedState.REST;
		else
			return null;
	}

}