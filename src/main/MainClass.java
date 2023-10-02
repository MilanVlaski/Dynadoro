package main;

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
		
		timer.begin();
	}

}
