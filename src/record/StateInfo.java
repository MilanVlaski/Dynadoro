package record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StateInfo {

	public enum State {
		IDLE("Idle", false),
		PAUSE("Paused", false),
		WORKING("Working", true),
		BREAK("Break", true);

		
		private final String name;
		private final boolean getsRecorded;

		private State(String name, boolean getsRecorded) {
			this.name = name;
			this.getsRecorded = getsRecorded;
		}

	}

	private final String name;
	private final Date startDate;
	private final int startTime;

	private Date endDate;

	private final boolean getsRecorded;

	public StateInfo(State state, int startTime) {
		this.name = state.name;
		this.startTime = startTime;
		this.getsRecorded = state.getsRecorded;
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

	public boolean matters() {
		return getsRecorded;
	}

}