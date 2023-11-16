package main;

import java.time.LocalDateTime;
import java.util.Scanner;

import display.ConsoleDisplay;
import display.Display;
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
//		startConsoleApplication();
	}

	private static void startSwingApplication()
	{
		Display display = new SwingDisplay();
		Timer timer = initTimer(display);
		startRecording(timer);
	}

	private static void startConsoleApplication()
	{
		Display display = new ConsoleDisplay();
		Timer timer = initTimer(display);
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

	private static Timer initTimer(Display display)
	{
		Counter counter = new ScheduledCounter(display);
		Timer timer = new Timer(display, counter, LocalDateTime.now());
		display.setModel(timer);
		return timer;
	}

	private static void startRecording(Timer timer)
	{
		UsageHistory file = new UsageHistory();
		UsageRecord record = new UsageRecord(file);
		timer.startRecording(record);
	}

}
