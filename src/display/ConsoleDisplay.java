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
		return displayedTime(time) + "\nPress 2 to take a break"
				+ "\nPress 3 to pause";
	}

	public static String breakMessage(int time) {
		return displayedTime(time) + "\nPress 1 to go back to work"
				+ "\nPress 3 to pause";
	}

	public static String workPauseMessage(int time) {
		return displayedTime(time) + "\nPress 4 to resume"
				+ "\nPress 2 to take a break";
	}

	public static String breakPauseMessage(int time) {
		return displayedTime(time) + "\nPress 4 to resume";
	}

	public static String breakFinishedMessage() {
		return displayedTime(0) + "\nBreak over!"
				+ "\nPress 1 to go back to work";
	}

	@Override
	protected void update(int time, DisplayState state) {

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
		case BREAK_PAUSE:
			System.out.println(breakPauseMessage(time));
			break;
		case WORK_PAUSE:
			System.out.println(workPauseMessage(time));
			break;
		case BREAK_FINISHED:
			System.out.println(breakFinishedMessage());
			break;
		}
	}

	@Override
	protected void update(int displayedTime) {
		update(displayedTime, state);
	}

}
