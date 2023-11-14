package record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

	private final Date startDate;
	private Date endDate;

	public StateData(State state, int startTime)
	{
		this.name = state.name;
		this.startDate = secondsToDate(startTime);
		this.getsRecorded = state.getsRecorded;
	}

	private Date secondsToDate(int seconds)
	{
		long milliseconds = (long) seconds * 1000;
		return new Date(milliseconds);
	}

	@Override
	public String toString()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEEE");
		DateFormat hourFormat = new SimpleDateFormat("HH:mm");

		String endTime = (endDate != null) ? hourFormat.format(endDate) : "unknown";

		return String.join(", ", dateFormat.format(startDate),
		        name, hourFormat.format(startDate), endTime);
	}

	public Date startDate()
	{ return startDate; }

	public boolean shouldBeRecorded()
	{ return getsRecorded; }

	public void finish(Date endDate)
	{ this.endDate = endDate; }

}