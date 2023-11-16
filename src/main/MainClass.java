package main;

import java.time.LocalDateTime;
import java.util.Scanner;

import display.ConsoleDisplay;
import display.swing.SwingDisplay;
import record.UsageHistory;
import record.UsageRecord;
import timer.Clock;
import timer.SystemClock;
import timer.Timer;
import timer.counter.Counter;
import timer.counter.ScheduledCounter;

public class MainClass
{

	public static void main(String[] args)
	{
		startSwingApplication();
//		startConsoleApplication(clock, dummyCounter);
	}

	private static void startSwingApplication()
	{
		SwingDisplay display = new SwingDisplay();
		Counter counter = new ScheduledCounter(display);
		Timer timer = new Timer(display, counter, LocalDateTime.now());
		display.setModel(timer);

		startRecording(timer);
	}

	private static void startRecording(Timer timer)
	{
		UsageHistory file = new UsageHistory();
		UsageRecord record = new UsageRecord(file);
		timer.startRecording(record);
	}

	private static void startConsoleApplication(Counter counter)
	{
		ConsoleDisplay display = new ConsoleDisplay();
		Timer timer = new Timer(display, counter, LocalDateTime.now());
		
		startRecording(timer);
		
		try (Scanner scanner = new Scanner(System.in))
		{
			while (true)
			{
				String input = scanner.nextLine();
				if (input.equals("1"))
					timer.begin(LocalDateTime.now());
				else if (input.equals("2"))
					timer.rest(LocalDateTime.now());
				else if (input.equals("3"))
					timer.pause(LocalDateTime.now());
				else if (input.equals("4"))
					timer.resume(LocalDateTime.now());
				else if (input.equals("5"))
					timer.reset(LocalDateTime.now());
			}
		}
	}
}
