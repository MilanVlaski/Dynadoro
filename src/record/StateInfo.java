package record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StateInfo {

	private final String name;
	private final Date start;

	private Date end;

	public StateInfo(String name, int startTime) {
		this.name = name;
		start = new Date(secondsToMillis(startTime));
	}

	private long secondsToMillis(int time) {
		return (long) time * 1000;
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEEE");
		DateFormat hourFormat = new SimpleDateFormat("HH:mm");

		return dateFormat.format(start) + ", " + name + ", "
				+ hourFormat.format(start) + ", " + "unknown";
	}
}
