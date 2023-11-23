package record;

import java.awt.Graphics;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Period
{
	public enum State
	{

		IDLE("Idle"),
		WORKING("Working"),
		RESTING("Resting"),
		PAUSE("Pause");

		String name;

		State(String name)
		{ this.name = name; }

		public static State ofString(String name)
		{
			String lowercaseName = name.toLowerCase();
			for (State state : values())
				if (lowercaseName.equals(state.name.toLowerCase()))
					return state;

			return null;
		}
	}

	private final State state;
	private final boolean getsRecorded;

	private final LocalDateTime start;
	private LocalDateTime end;

	public Period(State state, LocalDateTime start, boolean getsRecorded)
	{
		this.state = state;
		this.start = start;
		this.getsRecorded = getsRecorded;
	}

	public Period(State state, LocalDateTime start, LocalDateTime end)
	{
		this.state = state;
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
		        state.name, hourFormat.format(start), endTime);
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

	public State type()
	{ return state; }

	@Override
	public boolean equals(Object obj)
	{
		Period period = (Period) obj;
		return period.state.equals(state)
		        && period.start.equals(start)
		        && period.end.equals(end);
	}

}