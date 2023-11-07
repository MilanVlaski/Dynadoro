package record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StateInfo {

	private final String name;
	
	private final Date startDate;
	private final int startTime;

	private Date endDate;

	public StateInfo(String name, int startTime) {
		this.name = name;
		this.startTime = startTime;
		startDate = secondsToDate(startTime);
	}

	private Date secondsToDate(int time) {
		return new Date((long) time * 1000);
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd, EEEE");
		DateFormat hourFormat = new SimpleDateFormat("HH:mm");
		
		String endTime = endDate != null ? hourFormat.format(endDate) : "unknown";
		
		return String.join(", ", dateFormat.format(startDate), name,
				hourFormat.format(startDate), endTime);
	}

	public void finish(int endTime) {
		endDate = secondsToDate(endTime);
	}

	public int getStartTime() {
		return startTime;
	}
	
}