package main;

import java.time.LocalDateTime;

import display.Display;
import display.swing.SwingDisplay;
import recording.*;
import recording.clock.ClockFileMaker;
import recording.clock.ClockManager;
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
		History2 jsonHistory = new JsonHistory();
		Timer timer = new Timer(display, counter, history, LocalDateTime.now(), jsonHistory);
		display.setTimer(timer);
		//

		UsageRecord record = new UsageRecord(history);

		timer.startRecording(record);

		ClockFileMaker fileMaker = new ClockFileMaker();
		ClockManager clockManager = new ClockManager(fileMaker);

		timer.setClockManager(clockManager);
	}

}
