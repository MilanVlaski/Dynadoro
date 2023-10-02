package display;

public class ConsoleDisplay implements Display {

	@Override
	public String display(int seconds) {
		int hours = seconds / 3600;
	    int minutes = (seconds % 3600) / 60;
	    int remainingSeconds = seconds % 60;
	    
	    return (hours > 0 ? String.format("%02d:", hours) : "") +
	            String.format("%02d:%02d", minutes, remainingSeconds);
	}

}
