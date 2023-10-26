package main;

import java.util.Scanner;

import display.ConsoleDisplay;
import display.Display;
import timer.Clock;
import timer.SystemClock;
import timer.Timer;
import timer.counter.ScheduledCounter;
import timer.counter.Counter;

public class MainClass {

	public static void main(String[] args) {
		Clock clock = new SystemClock();
		Display display = new ConsoleDisplay();
		Counter counter = new ScheduledCounter();
		
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
					
			}
		}
	}

}
