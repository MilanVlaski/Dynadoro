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
			
			System.out.println(timer.time() + "\nPress 1 to start studying.");
			while(true) {
				String input = scanner.nextLine();
				if(input.equals("1")) {
					timer.begin();
					break;
				}
			}
		}
	}

}
