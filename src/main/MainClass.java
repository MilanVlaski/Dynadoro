package main;

import java.util.Scanner;

import display.ConsoleDisplay;
import display.SwingDisplay;
import record.UsageRecord;
import timer.Clock;
import timer.SystemClock;
import timer.Timer;
import timer.counter.Counter;
import timer.counter.ScheduledCounter;

public class MainClass {

	public static void main(String[] args) {
		Clock clock = new SystemClock();
		Counter counter = new ScheduledCounter();

		startSwingApplication(clock, counter);
//		startConsoleApplication(clock, counter);
	}

	private static void startSwingApplication(Clock clock, Counter counter) {
		SwingDisplay display = new SwingDisplay();
		Timer timer = new Timer(clock, display, counter);
//		timer.startRecording(new UsageRecord(new ));
		display.setModel(timer);
	}

	private static void startConsoleApplication(Clock clock,  Counter counter) {
		ConsoleDisplay display = new ConsoleDisplay();
		Timer timer = new Timer(clock, display, counter);
		
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				String input = scanner.nextLine();
				if (input.equals("1"))
					timer.begin();
				else if (input.equals("2"))
					timer.takeBreak();
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
