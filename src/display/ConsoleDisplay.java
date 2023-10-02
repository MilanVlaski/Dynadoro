package display;

public class ConsoleDisplay implements Display {

	@Override
	public String display(int seconds) {
		int hours = seconds / 3600;
	    int minutes = (seconds % 3600) / 60;
	    int remainingSeconds = seconds % 60;
	    
	    Time time = new Time(hours, minutes, remainingSeconds);
	    return time.toString();
	}

	private static class Time {
		private final String hours;
		private final String minutes;
		private final String seconds;
		
		public Time(int hours, int minutes, int seconds) {
			this.hours = stringOfTime(hours);
			this.minutes = stringOfTime(minutes);
			this.seconds = stringOfTime(seconds);
		}
		
		private static String stringOfTime(int timeUnit) {
			return String.format("%02d", timeUnit);
		}
		
		@Override
		public String toString() {
			if(!hours.equals("00"))
				return String.join(":", hours, minutes, seconds);
			else
				return String.join(":", minutes, seconds);
		}
	}
}
