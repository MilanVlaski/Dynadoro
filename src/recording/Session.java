package recording;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Session
{

	private final State state;
	private final LocalDate date;
	private final LocalTime start;
	private final LocalTime end;

	public Session(State state, LocalDate date, LocalTime start, LocalTime end)
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
	public Session(State state, LocalDateTime start, LocalDateTime end)
	{ this(state, start.toLocalDate(), start.toLocalTime(), end.toLocalTime()); }

	public State type()
	{ return state; }

	public LocalTime startTime()
	{ return start; }

	public LocalTime endTime()
	{ return end; }

	public Duration duration()
	{ return Duration.between(start, end); }

	public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
		Session session = (Session) obj;
		return session.state.equals(state) && session.start.equals(start)
		        && session.end.equals(end);
	}

	public LocalDate date()
	{ return date; }

}