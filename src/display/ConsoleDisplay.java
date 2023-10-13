package display;

public class ConsoleDisplay extends Display {

	public static String displayedTime(int seconds) {
		int hours = seconds / 3600;
	    int minutes = (seconds % 3600) / 60;
	    int remainingSeconds = seconds % 60;
	    
	    return (hours > 0 ? String.format("%02d:", hours) : "") +
	            String.format("%02d:%02d", minutes, remainingSeconds);
	}

	public static String displayIdle() {
		return displayedTime(0) + "\nPress 1 to start studying";
	}

	public static String displayWorking(int time) {
		return displayedTime(time) + "\nPress 1 to take a break";
	}

	public static String displayBreak(int time) {
		return displayedTime(time) + "\nPress 1 to go back to work";
	}

	
}
