package recording;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Period
{

	private final State state;
	private final LocalDate date;
	private final LocalTime start;
	private final LocalTime end;

//	public Period(State state, LocalDateTime start, LocalDateTime end)
//	{
//		this.state = state;
//		this.start = start;
//		this.end = end;
//	}

	public Period(State state, LocalDate date, LocalTime start, LocalTime end)
	{
		this.state = state;
		this.date = date;
		this.start = start;
		this.end = end;
	}

	/**
	 * For easier testing!
	 * 
	 * @param working
	 * @param start
	 * @param end
	 */
	public Period(State state, LocalDateTime start, LocalDateTime end)
	{
		this(state, start.toLocalDate(), start.toLocalTime(), end.toLocalTime());
	}

	public State type()
	{ return state; }

	public LocalTime startTime()
	{ return start; }

	public LocalTime endTime()
	{ return end; }

	public Duration duration()
	{
		return Duration.between(start, end);
	}

	public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static DateTimeFormatter weekdayFormat = DateTimeFormatter.ofPattern("EEEE");
	public static DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH:mm");
	public static String regex = "(\\d{4}\\-\\d{2}\\-\\d{2}),\\s*(\\w+),\\s(\\d+:\\d+).*?(\\d+:\\d+)";

	@Override
	public String toString()
	{
		return String.join(", ", dateFormat.format(date), state.name,
		                   hourFormat.format(start), hourFormat.format(end));
	}

	@Override
	public boolean equals(Object obj)
	{
		Period period = (Period) obj;
		return period.state.equals(state) && period.start.equals(start)
		        && period.end.equals(end);
	}

	public LocalDate date()
	{ return date; }

}