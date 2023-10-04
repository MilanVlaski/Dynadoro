package display;

import timer.Timer.TimerState;

public class ConsoleDisplay implements Display {

	public String displayedTime(int seconds) {
		int hours = seconds / 3600;
	    int minutes = (seconds % 3600) / 60;
	    int remainingSeconds = seconds % 60;
	    
	    return (hours > 0 ? String.format("%02d:", hours) : "") +
	            String.format("%02d:%02d", minutes, remainingSeconds);
	}
	
	@Override
	public void display(int seconds, TimerState state) {
		System.out.println(displayedMessage(seconds, state));
	}

	public String displayedMessage(int time, TimerState state) {
		if(state == TimerState.IDLE)
			return displayedTime(0) + "\nPress 1 to start studying.";
		else
			return displayedTime(time) + "\nPress 2 to take a break.";
	}

	
}
