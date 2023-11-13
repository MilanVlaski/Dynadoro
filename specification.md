## REQUIREMENTS

- When I open the app I want it to display 00:00.
- When I press Begin it counts up, and shows me that I am working. (You have the option of going on break).
- When I press Break, it start counting down for a period 5 times shorter than the time spent working.
- When it reaches zero, it plays an alarm.

- Cancel at any time.
- Pause and resume at any time, while resting or working.

## TODO
- i want to push Clock interface to outside collaborators (Display and Counter), because
they are the only ones that need clock. then they can provide a Moment object as parameter,
and from there we can manipulate those as we see fit, but there is no need for mocking.
- For Day visualization the input is StateData and output is a .png. But an intermediate step could be turning the startTime and endTime into Period objects that hold data about a period of time spent working (from 12:00 to 12:30). (fields from and to, maybe). And then these periods are used as drawing data.
- study java.util.time
- Moment returns an int, representing time that passed. But it would be nice if it could convert to Date, with all data that that entails.

- while break ticks down you could have a charging battery filling up (maybe in thirds, battery with 3 bars)

- remove the window bar and add rounded borders (same color as text maybe)

## LOGS:

- If you exit the app, the previous state needs to finish.

- We have our UsageFile and our VisualDay. When we select the option to show these images, it shows all of them, with (November, 23, Tuesday... + Thumbnail or full image). We keep the images and file consistent by loading the file data to memory (creating objects out of collections of StateInfos and mapping them to images). If extra images exist, we delete them. And if an object (a Day), doesn't have an image (Tuesday_23_November.png - DayVisual), it gets created. A Day has States, and a Day must have a VisualDay. States have start and end time. (Based on this you can make an arc. Combining the arcs gives you whole picture). (we don't care about a day that doesn't have states, it doesn't get logged).

- Creating images for date (displays Date + Day of the week)
- hours studying per day
- hours studying per week
- ---

### PERHAPS
- time and period, instead of being an integer, should be refactored to value objects?
- Potential errors if you work for less than a second, or less than 5.
---

### BUGFIX:
 When pausing while on break the timer correctly stops counting. But it incorrectly says Break over! when and displays 00:00.
FIXED: It was because the counter is a little weirdo. Scheduled tasks can't be stopped, i had to add logic that prevents that. I don't understand the library fully...
BUG: If you pause and resume before the original break interval, you get alarmed twice.
FIXED: shutdowNow() does a full shutdown, so that works.
TODO: Test this bug, and fix it