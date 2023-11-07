package record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StateInfo {

	private final String name;
	private final int startTime;
	private int year;

	public StateInfo(String name, int startTime) {
		this.name = name;
		this.startTime = startTime;
		
		long time = startTime;
		Date date = new Date(time * 1000);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	}

	@Override
	public String toString() {
		return year + "-11-07, Tuesday, " + name + ", 14:00, unknown";
	}
}
