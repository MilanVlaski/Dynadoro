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
- We keep the images and file consistent by loading the file data to memory
- A Day has StateData, and a Day must have a VisualDay.
- If extra VisualDays exist, we delete them. And if a Day, doesn't have an image (Tuesday_23_November.png - DayVisual), it gets created.
- hours studied per day
- hours studied per week
- If you exit the app, the previous state needs to finish.

- while break ticks down you could have a charging battery filling up (maybe in thirds, battery with 3 bars)
- remove the window bar and add rounded borders (same color as text maybe)

#### PERHAPS
- Potential errors if you work for less than a second, or less than 5.

#### BUGFIX:
 When pausing while on break the timer correctly stops counting. But it incorrectly says Break over! when and displays 00:00.
FIXED: It was because the counter is a little weirdo. Scheduled tasks can't be stopped, i had to add logic that prevents that. I don't understand the library fully...
BUG: If you pause and resume before the original break interval, you get alarmed twice.
FIXED: shutdowNow() does a full shutdown, so that works.
TODO: Test this bug, and fix it