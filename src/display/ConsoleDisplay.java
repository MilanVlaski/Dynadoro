package display;

public class ConsoleDisplay implements Display {

	@Override
	public String display(int seconds) {
		int hours = seconds / 3600;
	    int minutes = (seconds % 3600) / 60;
	    int remainingSeconds = seconds % 60;
	    
		String hoursString = stringOfTime(hours);
		String minutesString = stringOfTime(minutes);
		String secondsString = stringOfTime(remainingSeconds);
	    
	    if(hours != 0)
			return String.join(":", hoursString, minutesString, secondsString);
		else
			return String.join(":", minutesString, secondsString);
	}

	private static String stringOfTime(int timeUnit) {
		return String.format("%02d", timeUnit);
	}
}
