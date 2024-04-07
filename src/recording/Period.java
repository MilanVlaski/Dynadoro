package recording;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Period
{

	private final State state;
	private final LocalDateTime start;
	private final LocalDateTime end;

	public Period(State state, LocalDateTime start, LocalDateTime end)
	{
		this.state = state;
		this.start = start;
		this.end = end;
	}

	public State type()
	{ return state; }

	public LocalDateTime startTime()
	{ return start; }

	public Duration duration()
	{
		return Duration.between(start, end);
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

	public LocalDate date()
	{ return start.toLocalDate(); }

}