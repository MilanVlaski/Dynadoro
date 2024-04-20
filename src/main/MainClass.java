package main;

import java.time.LocalDateTime;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import display.swing.SwingDisplay;
import recording.SessionHistory;
import sound.SoundPlayer;
import timer.Timer;
import timer.counter.ScheduledCounter;

public class MainClass
{

	public static void main(String[] args)
	{
		var context = new AnnotationConfigApplicationContext(AppConfiguration.class);
//		startSwingApplication();
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
