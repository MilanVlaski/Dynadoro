package main;

import java.time.LocalDateTime;

import display.swing.SwingDisplay;
import recording.*;
import recording.clock.ClockFileMaker;
import recording.clock.ClockManager;
import sound.SoundPlayer;
import timer.Timer;
import timer.counter.ScheduledCounter;

public class MainClass
{

	public static void main(String[] args)
	{
		startSwingApplication();
	}

	private static void startSwingApplication()
	{
		History history = new UsageHistory("periods");

		var display = new SwingDisplay();
		var counter = new ScheduledCounter(display, new SoundPlayer());
		var history2 = new RealHistory(history);
		Timer timer = new Timer(display, counter, history, LocalDateTime.now(),
		        history2);
		display.setTimer(timer);

		var fileMaker = new ClockFileMaker();
		var clockManager = new ClockManager(fileMaker);

		timer.setClockManager(clockManager);
	}

}
