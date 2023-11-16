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
		Clock clock = new SystemClock();
		Counter counter = new ScheduledCounter();

		startSwingApplication(clock, counter);
//		startConsoleApplication(clock, dummyCounter);
	}

	private static void startSwingApplication(Clock clock, Counter counter)
	{
		SwingDisplay display = new SwingDisplay();
		Timer timer = new Timer(clock, display, counter, LocalDateTime.now());
		display.setModel(timer);

		startRecording(timer);
	}

	private static void startRecording(Timer timer)
	{
		UsageHistory file = new UsageHistory();
		UsageRecord record = new UsageRecord(file);
		timer.startRecording(record);
	}

	private static void startConsoleApplication(Clock clock, Counter counter)
	{
		ConsoleDisplay display = new ConsoleDisplay();
		Timer timer = new Timer(clock, display, counter, LocalDateTime.now());
		
		startRecording(timer);
		
		try (Scanner scanner = new Scanner(System.in))
		{
			while (true)
			{
				String input = scanner.nextLine();
				if (input.equals("1"))
					timer.begin();
				else if (input.equals("2"))
					timer.rest();
				else if (input.equals("3"))
					timer.pause();
				else if (input.equals("4"))
					timer.resume();
				else if (input.equals("5"))
					timer.reset();
			}
		}
	}
}
