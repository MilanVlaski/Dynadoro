package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.nio.file.Path;
import java.time.*;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import recording.Day;
import recording.Session;
import recording.State;
import recording.clock.*;

public class TestAssigningClocksToDays
{

	@Mock
	ClockFileMaker fakeFileMaker;

	@InjectMocks
	ClockManager clockManager;

	@BeforeEach
	void setup()
	{ MockitoAnnotations.openMocks(this); }

	static Session seventhNovember = new Session(State.WORKING,
	        LocalDate.of(2023, 11, 7),
	        LocalTime.of(0, 0),
	        LocalTime.of(0, 50));
	static Session nineteenthJune = new Session(State.WORKING,
	        LocalDate.of(2023, 6, 19),
	        LocalTime.of(0, 0),
	        LocalTime.of(0, 50));

	@Test
	void AssignsClockToCorrectDay()
	{
		List<Day> days = List.of(new Day(List.of(seventhNovember)));
		ProductivityClock clock = new ProductivityClock(days.get(0));

		clockManager.assignClocksToDays(List.of(clock), days);

		assertEquals(clock, days.get(0).clock());
	}

	@Test
	void CreatesNewClock_AndAssignsIt_IfClockHasDifferentDate()
	{
		List<Day> days = List.of(new Day(List.of(seventhNovember)));
		ProductivityClock clock = new ProductivityClock(Path.of("11_07_2023.lala"));

		clockManager.assignClocksToDays(List.of(clock), days);

		Day day = days.get(0);
		ProductivityClock expected = new ProductivityClock(day);
		assertEquals(expected, day.clock());

		verify(fakeFileMaker).makeClockFile(day);
	}

	@Test
	void CreatesNewClock_AndAssignsIt_IfNoClocksExist()
	{
		List<ProductivityClock> noClocks = Collections.emptyList();
		List<Day> days = List.of(new Day(List.of(seventhNovember)));

		clockManager.assignClocksToDays(noClocks, days);

		Day day = days.get(0);
		ProductivityClock expected = new ProductivityClock(day);
		assertEquals(expected, day.clock());

		verify(fakeFileMaker).makeClockFile(day);
	}

	// if a clock exists, and the day isn't over, the image may need redrawing
	@Test
	void CreatesNewClock_AndAssignsIt_IfClockWasMadeToday_FLAKY()
	{
		LocalDateTime now = LocalDateTime.now();
		LocalTime time = now.toLocalTime();
		Session todaysPeriod = new Session(State.WORKING, now.toLocalDate(), time, time);

		List<Day> days = List.of(new Day(List.of(todaysPeriod)));
		Day day = days.get(0);

		ProductivityClock todaysClock = new ProductivityClock(day);
		List<ProductivityClock> clocks = List.of(todaysClock);
		//

		clockManager.assignClocksToDays(clocks, days);

		assertEquals(todaysClock, day.clock());
		verify(fakeFileMaker).makeClockFile(day);
	}
}