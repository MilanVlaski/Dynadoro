package main;

import java.util.Scanner;

import display.ConsoleDisplay;
import display.Display;
import timer.Clock;
import timer.SystemClock;
import timer.Timer;

public class MainClass {

	public static void main(String[] args) {
		Display display = new ConsoleDisplay();
		Clock clock = new SystemClock();
		Timer timer = new Timer(clock, display);

		try (Scanner scanner = new Scanner(System.in)) {
			
			System.out.println("00:00"
					+ "\nPress 1 to start studying.");
			String input = scanner.nextLine();
			while (true) {
				if (input.equals("1")) {
					timer.begin();
					break;
				}
			}
		}
	}

}
