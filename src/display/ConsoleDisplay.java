package display;

import java.time.LocalDateTime;

import timer.Timer;

public class ConsoleDisplay extends Display
{

	private DisplayState state;

	public static String displayedTime(int seconds)
	{
		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		int remainingSeconds = seconds % 60;

		return (hours > 0 ? String.format("%02d:", hours) : "") +
		        String.format("%02d:%02d", minutes, remainingSeconds);
	}

	public static String displayedMinutes(int mins)
	{
		int hours = mins / 60;
		int minutes = mins % 60;

		return String.format("%02d:%02d", hours, minutes);
	}

	public static String idleMessage()
	{ return displayedTime(0) + "\nPress 1 to start studying"; }

	public static String workingMessage(int time)
	{ return displayedTime(time) + "\nPress 2 to take a break"
	        + "\nPress 3 to pause"; }

	public static String restMessage(int time)
	{ return displayedTime(time) + "\nPress 1 to go back to work"
	        + "\nPress 3 to pause"; }

	public static String workPauseMessage(int time)
	{ return displayedTime(time) + "\nPress 4 to resume"
	        + "\nPress 2 to take a break"; }

	public static String restPauseMessage(int time)
	{ return displayedTime(time) + "\nPress 4 to resume"; }

	public static String restFinishedMessage()
	{ return displayedTime(0) + "\nBreak over!"
	        + "\nPress 1 to go back to work"; }

	@Override
	public void pauseWork()
	{ System.out.println(workPauseMessage(secondsNow())); }

	@Override
	public void pauseRest()
	{ System.out.println(restPauseMessage(secondsNow())); }

	private int secondsNow()
	{ return timer.seconds(LocalDateTime.now()); }

	@Override
	public void finishRest()
	{ System.out.println(restFinishedMessage()); }

	@Override
	public void showResting()
	{ System.out.println(restMessage(secondsNow())); }

	@Override
	public void showWorking()
	{ System.out.println(workingMessage(secondsNow())); }

	@Override
	public void showIdle()
	{ System.out.println(idleMessage()); }

	@Override
	public void show(int displayedTime)
	{ updateState(state); }

	@Override
	public void show(int displayedTime, DisplayState state)
	{
		this.state = state;
		updateState(state);
	}

	@Override
	public void tickTime()
	{ show(secondsNow()); }

	@Override
	public void setTimer(Timer timer)
	{ this.timer = timer; }

}
