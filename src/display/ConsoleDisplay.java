package display;

public class ConsoleDisplay extends Display {

	public static String displayedTime(int seconds) {
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		int remainingSeconds = seconds % 60;

		return (hours > 0 ? String.format("%02d:", hours) : "") +
				String.format("%02d:%02d", minutes, remainingSeconds);
	}

	public static String idleMessage() {
		return displayedTime(0) + "\nPress 1 to start studying";
	}

	public static String workingMessage(int time) {
		return displayedTime(time) + "\nPress 2 to take a break";
	}

	public static String breakMessage(int time) {
		return displayedTime(time) + "\nPress 1 to go back to work";
	}

	@Override
	public void show() {
		showWith(time, state);
	}

	@Override
	public void show(int displayedTime) {
		time = displayedTime;
		show();
	}

	@Override
	public void show(int displayedTime, DisplayState state) {
		this.state = state;
		time = displayedTime;
		show();
	}

	private void showWith(int time, DisplayState state) {
		switch (state) {
		case IDLE:
			System.out.println(idleMessage());
			break;
		case WORKING:
			System.out.println(workingMessage(time));
			break;
		case TAKING_BREAK:
			System.out.println(breakMessage(time));
			break;
		}
	}
}
