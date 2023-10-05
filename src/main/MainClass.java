package main;

import java.util.Scanner;

import display.ConsoleDisplay;
import display.Display;
import timer.Clock;
import timer.SystemClock;
import timer.Timer;

public class MainClass {

	public static void main(String[] args) {
		Clock clock = new SystemClock();
		Timer timer = new Timer(clock);

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				String input = scanner.nextLine();
				if (input.equals("1")) {
					timer.begin();
				} else if (input.equals("2")) {
					timer.takeBreak();
				}
			}
		}
	}

}
