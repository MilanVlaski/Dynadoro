package main;

import java.time.LocalDateTime;

import display.Display;
import display.swing.SwingDisplay;
import record.*;
import record.clock.ClockFileMaker;
import record.clock.ClockManager;
import sound.SoundPlayer;
import timer.Timer;
import timer.counter.Counter;
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

		Display display = new SwingDisplay();
		Counter counter = new ScheduledCounter(display, new SoundPlayer());
		Timer timer = new Timer(display, counter, history, LocalDateTime.now());
		display.setTimer(timer);
		//
		
		UsageRecord record = new UsageRecord(history);

		timer.startRecording(record);

		ClockFileMaker fileMaker = new ClockFileMaker();
		ClockManager clockManager = new ClockManager(history, fileMaker);

		timer.setClockManager(clockManager);
	}

}
