package record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StateData
{

	public enum State
	{
		IDLE("Idle", false),
		PAUSE("Paused", false),
		WORKING("Working", true),
		RESTING("Resting", true);

		private final String name;
		private final boolean getsRecorded;

		private State(String name, boolean getsRecorded)
		{
			this.name = name;
			this.getsRecorded = getsRecorded;
		}

	}

	private final String name;
	private final boolean getsRecorded;

	private Date startDate;
	private Date endDate;
	private LocalDateTime start;
	private LocalDateTime end;

	public StateData(State state, int startTime)
	{
		this.name = state.name;
		this.startDate = secondsToDate(startTime);
		this.getsRecorded = state.getsRecorded;
	}

	public StateData(String name, LocalDateTime start, boolean getsRecorded)
	{
		this.name = name;
		this.start = start;
		this.getsRecorded = getsRecorded;
	}

	private Date secondsToDate(int seconds)
	{
		long milliseconds = (long) seconds * 1000;
		return new Date(milliseconds);
	}

	@Override
	public String toString()
	{
		DateTimeFormatter dateFormat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd, EEEE");
		DateTimeFormatter hourFormat1 = DateTimeFormatter.ofPattern("HH:mm");

		String endTime1 = (end != null) ? hourFormat1.format(end) : "unknown";

		return String.join(", ", dateFormat1.format(start),
		        name, hourFormat1.format(start), endTime1);
	}

	public Date startDate()
	{ return startDate; }

	public boolean shouldBeRecorded()
	{ return getsRecorded; }

	public void finish(Date endDate)
	{ this.endDate = endDate; }

}