package record;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Period
{

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

	/**
	 * Periods initialized with this constructor are recorded, by default.
	 */
	public Period(State state, LocalDateTime start, LocalDateTime end)
	{
		this.state = state;
		this.start = start;
		this.end = end;
		this.getsRecorded = true;
	}

	public LocalDateTime startTime()
	{ return start; }

	public boolean shouldBeRecorded()
	{ return getsRecorded; }

	public State type()
	{ return state; }

	public void finish(LocalDateTime end)
	{ this.end = end; }

	public Duration duration()
	{
		if (end != null)
			return Duration.between(start, end);
		else
			return Duration.ZERO;
	}

	public static final DateTimeFormatter dateFormat = DateTimeFormatter
	        .ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter weekdayFormat = DateTimeFormatter
	        .ofPattern("EEEE");
	public static final DateTimeFormatter hourFormat = DateTimeFormatter
	        .ofPattern("HH:mm");
	public static final String regex = "(\\d{4}\\-\\d{2}\\-\\d{2}),\\s*(\\w+),\\s*(\\w+),\\s(\\d+:\\d+).*?(\\d+:\\d+)";

	@Override
	public String toString()
	{
		String endTime = (end != null) ? hourFormat.format(end) : "unknown";

		return String.join(", ", dateFormat.format(start), weekdayFormat.format(start),
		        state.name, hourFormat.format(start), endTime);
	}

	@Override
	public boolean equals(Object obj)
	{
		Period period = (Period) obj;
		return period.state.equals(state)
		        && period.start.equals(start)
		        && period.end.equals(end);
	}

	public boolean isSameDateAs(Period period)
	{ return start.toLocalDate().equals(period.start.toLocalDate()); }
}