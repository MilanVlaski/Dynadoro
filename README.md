## Use cases
- When I open the app I want it to display 00:00.
- When I press Begin it counts up, and shows me that I am working. (You have the option of going on break).
- When I press Break, it start counting down for a period 5 times shorter than the time spent working.
- When it reaches zero, it plays an alarm.
- Cancel at any time.
- Pause and resume at any time, while resting or working.
- Logs your sessions in a file.
- I can see images of clocks representing my past study and break sessions in a day. I can see which date and weekday the clocks belong to. When I open a clock, I see an enlarged image, with a big date and weekday at the bottom. Also time spent working and resting.

#### Classifying sessions
- I can set a tag while using the app, to differentiate study/work sessions (Reading, revising, casual reading...).
- The tag can be typed in.
	- The tag stays visible at all times, unless opened, and then a different name can be typed in.
	- The app remembers previous tag names, and shows them in the dropdown.
- When checking history, I want to be able to have a list of tags, and be able to toggle them on or off. The display then updates to show only days when I studied, and only study sessions.
- All sessions are tagged as STUDY by default.
- When checking history, STUDY is the default selected tag, if the user has never used tags other than STUDY. If other tags exist, they are all shown. (Or should only study be shown anyway?) (If all tags are shown, how can I differentiate between them visually?) (Perhaps adjacent sessions with the same tag should have some kind of border...).


## TODO
- [ ] If there are overlapping sessions in a day then show AM and PM clock (AM clock is lighter, while PM is darker? Or they can swap colors.)
	- [ ] And if no overlaps, then the display should show one clock, AM or PM
- Write the main method using spring context, just to try it
- Sound isnt working, so make it a maven project, with the maven structure.
- show hours studied per week
- make sessions file openable through UI

#### PERHAPS
- Potential errors if you work for less than a second, or less than 5.

#### BUGFIX:
 When pausing while on break the timer correctly stops counting. But it incorrectly says Break over! when and displays 00:00.
FIXED: It was because the counter is a little weirdo. Scheduled tasks can't be stopped, i had to add logic that prevents that. I don't understand the library fully...
BUG: If you pause and resume before the original break interval, you get alarmed twice.
FIXED: shutdowNow() does a full shutdown, so that works.
TODO: Test this bug, and fix it