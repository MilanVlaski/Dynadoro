package record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StateInfo {

	private final String name;
	private final Date start;

	private Date end;

	public StateInfo(String name, int startTime) {
		this.name = name;
		start = secondsToDate(startTime);
	}

	private Date secondsToDate(int time) {
		return new Date((long) time * 1000);
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEEE");
		DateFormat hourFormat = new SimpleDateFormat("HH:mm");

		return dateFormat.format(start) + ", " + name + ", "
				+ hourFormat.format(start) + ", " + endTimeString(hourFormat);
	}

	private String endTimeString(DateFormat hourFormat) {
		return end != null ? hourFormat.format(end) : "unknown";
	}

	public void finish(int endTime) {
		end = secondsToDate(endTime);
	}
}
