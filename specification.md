## REQUIREMENTS

- When I open the app I want it to display 00:00.
- When I press Begin it counts up, and shows me that I am working. (You have the option of going on break).
- When I press Break, it start counting down for a period 5 times shorter than the time spent working.
- When it reaches zero, it plays an alarm.

- Cancel at any time.
- Pause and resume at any time, while resting or working.

- Logs your sessions in a file.
- I can see images of clocks representing my study and break sessions in a day. I can see which date and weekday the clocks belong to. When I open a clock, I see an enlarged image, with a big date and weekday at the bottom.

## TODO
- Load Day with StateData. A Clock must be created. Lets say Work starts at 00:00 ends at 00:25. So 0 is the startAngle, and 25 is the arcAngle. Rest from 00:25 to 00:30, means 25 is the startAngle and 5 is the arcAngle. So we convert the startTime to a startAngle, and the value we check is number of minutes, so instead of 12:45 its 12 * 60 + 45. And duration, expressed in minutes, is our arcAngle.
---
- We keep the images and file consistent by loading the file data to memory (creating Days out of collections of StateData and creating images belonging to days).
- A Day has States, and a Day must have a VisualDay. States have start and end time. (Based on this you can make an arc. Combining the arcs gives you whole picture). (we don't care about a day that doesn't have states, it doesn't get logged).
- If extra images exist, we delete them. And if an object (a Day), doesn't have an image (Tuesday_23_November.png - DayVisual), it gets created.
- For Day visualization the input is StateData (belonging to a certain day) and output is a .png. But an intermediate step could be turning the startTime and endTime into Period objects that hold data about a period of time spent working (from 12:00 to 12:30). (fields from and to, maybe). And then these periods are used as drawing data.
- hours studied per day
- hours studied per week
- If you exit the app, the previous state needs to finish.

- while break ticks down you could have a charging battery filling up (maybe in thirds, battery with 3 bars)
- remove the window bar and add rounded borders (same color as text maybe)
- ---
### PERHAPS
- Potential errors if you work for less than a second, or less than 5.
---
### BUGFIX:
 When pausing while on break the timer correctly stops counting. But it incorrectly says Break over! when and displays 00:00.
FIXED: It was because the counter is a little weirdo. Scheduled tasks can't be stopped, i had to add logic that prevents that. I don't understand the library fully...
BUG: If you pause and resume before the original break interval, you get alarmed twice.
FIXED: shutdowNow() does a full shutdown, so that works.
TODO: Test this bug, and fix it