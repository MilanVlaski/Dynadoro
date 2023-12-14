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
	protected void pauseWork()
	{ System.out.println(workPauseMessage(timer.seconds(LocalDateTime.now()))); }

	@Override
	protected void pauseRest()
	{ System.out.println(restPauseMessage(timer.seconds(LocalDateTime.now()))); }

	@Override
	protected void finishRest()
	{ System.out.println(restFinishedMessage()); }

	@Override
	protected void showResting()
	{ System.out.println(restMessage(timer.seconds(LocalDateTime.now()))); }

	@Override
	protected void showWorking()
	{ System.out.println(workingMessage(timer.seconds(LocalDateTime.now()))); }

	@Override
	protected void showIdle()
	{ System.out.println(idleMessage()); }

	@Override
	protected void updateTime(int displayedTime)
	{ updateState(state); }

	@Override
	protected void updateTimeAndState(int displayedTime, DisplayState state)
	{
		this.state = state;
		updateState(state);
	}

	@Override
	public void tickTime()
	{ show(timer.seconds(LocalDateTime.now())); }

	@Override
	public void setModel(Timer timer)
	{ this.timer = timer; }

}
