package main;

import java.time.LocalDateTime;

import display.swing.SwingDisplay;
import recording.SessionHistory;
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
		var display = new SwingDisplay();
		var counter = new ScheduledCounter(display, new SoundPlayer());
		var history2 = new SessionHistory("sessions");
		Timer timer = new Timer(display, counter, LocalDateTime.now(), history2);
		display.setTimer(timer);
	}

}
