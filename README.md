<img height="120" alt="dynadoro" src="https://github.com/user-attachments/assets/b5bf33a0-dc77-4f13-aa5c-be00dc9c8de7" />

[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/MilanVlaski/Dynadoro/blob/main/LICENSE)
<!-- ![Build](https://img.shields.io/github/actions/workflow/status/USER/REPO/ci.yml?branch=main) -->

Dynadoro is a study/work timer, based on the [pomodoro technique](https://en.wikipedia.org/wiki/Pomodoro_Technique). Usually, the technique involves 25 minutes of work, and 5 minutes of rest. I found that annoying, so I decided to make an app that lets me work as long, or short, as I want, and then resting for a time that maintains the pomodoro ratio.

## How to run?
To use the app, you must have JDK 21 installed, as well as Maven. We suggest installing Maven with `sdk maven install` using [SDKMAN](https://sdkman.io/).
1. Clone the repository, and `cd` into it.
2. Run `mvn clean install`
3. Run `mvn clean compile assembly:single`
4. To run the jar file, run `java -jar target/DynamicPomodoro-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
   
**Note**: The app will create a file in your home folder, i.e. on Linux `~/Dynadoro/sessions.txt`. It's a simple text file, keeping track of all of your study sessions, so that they can be visualized.

## Use cases

### Tracking Example Visualization
<img width="913" height="459" alt="image" src="https://github.com/user-attachments/assets/96a52819-7221-4c67-bb69-ce533fc9de88" />

- When I open the app I want it to display 00:00.
- When I press Begin it counts up, and shows me that I am working. (You have the option of going on break).
- When I press Break, it start counting down for a period 5 times shorter than the time spent working.
- When it reaches zero, it plays an alarm.
- Cancel at any time.
- Pause and resume at any time, while resting or working.
- Logs your sessions in a file.
- I can see images of clocks representing my past study and break sessions in a day. I can see which date and weekday the clocks belong to. When I open a clock, I see an enlarged image, with a big date and weekday at the bottom. Also time spent working and resting.


## Status

The app is usable on desktop environments.

## How to contribute

In progress.

## TODO
- [ ] If there are overlapping sessions in a day then show AM and PM clock (AM clock is lighter, while PM is darker? Or they can swap colors.)
	- [ ] And if no overlaps, then the display should show one clock, AM or PM
- Write the main method using spring context, just to try it
- Sound isnt working, so make it a maven project, with the maven structure.
- show hours studied per week
- make sessions file openable through UI
