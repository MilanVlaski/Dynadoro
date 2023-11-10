package display;

public class ConsoleDisplay extends Display
{

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
	protected void pauseWork() { System.out.println(workPauseMessage(time)); }

	@Override
	protected void pauseRest() { System.out.println(breakPauseMessage(time)); }

	@Override
	protected void finishRest() { System.out.println(breakFinishedMessage()); }

	@Override
	protected void showResting() { System.out.println(breakMessage(time)); }

	@Override
	protected void showWorking() { System.out.println(workingMessage(time)); }

	@Override
	protected void showIdle() { System.out.println(idleMessage()); }

	@Override
	protected void updateTime(int displayedTime) { updateState(state); }

	@Override
	protected void updateTimeAndState(int displayedTime, DisplayState state) {
		updateState(state);
	}

}
