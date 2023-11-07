package record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StateInfo {

	private final String name;
	private final long startTime;
	private int endTime;

	private Date date;

	public StateInfo(String name, long startTime) {
		this.name = name;
		this.startTime = startTime;

		long time = startTime * 1000;
		date = new Date(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEEE");
		DateFormat hourFormat = new SimpleDateFormat("HH:mm");

		String endTimeString = endTime == 0 ? "unknown" : hourFormat.format(new Date(endTime));

		return dateFormat.format(date) + ", " + name + ", "
				+ hourFormat.format(date) + ", " + endTimeString;
	}
}
