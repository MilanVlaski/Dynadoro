package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import record.*;
import record.clock.*;

public class TestAssigningClocks
{

	@Mock
	ClockFileMaker dummyFileMaker;

	@InjectMocks
	ClockManager clockManager;

	@BeforeEach
	void setup()
	{ MockitoAnnotations.openMocks(this); }

	static Period seventhNovember = new Period(State.WORKING,
	        LocalDateTime.of(2023, 11, 7, 0, 0),
	        LocalDateTime.of(2023, 11, 7, 0, 50));
	static Period nineteenthJune = new Period(State.WORKING,
	        LocalDateTime.of(2023, 6, 19, 0, 0),
	        LocalDateTime.of(2023, 6, 19, 0, 0));

	@Test
	void AssignsClockToCorrectDay()
	{
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));
		ProductivityClock clock = new ProductivityClock(days.get(0));

		clockManager.assignClocksToDays(List.of(clock), days);

		assertEquals(clock, days.get(0).clock());
	}

	@Test
	void CreatesNewClock_AndAssignsIt_IfClockHasDifferentDate()
	{
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));
		ProductivityClock clock = new ProductivityClock(Path.of("11_07_2023.lala"));

		clockManager.assignClocksToDays(List.of(clock), days);

		Day day = days.get(0);
		ProductivityClock expected = new ProductivityClock(day);
		assertEquals(expected, day.clock());

		verify(dummyFileMaker).makeClockFile(day);
	}

	@Test
	void CreatesNewClock_AndAssignsIt_IfNoClocksExist()
	{
		List<ProductivityClock> noClocks = Collections.emptyList();
		List<Day> days = ClockManager.createDays(List.of(seventhNovember));

		clockManager.assignClocksToDays(noClocks, days);

		Day day = days.get(0);
		ProductivityClock expected = new ProductivityClock(day);
		assertEquals(expected, day.clock());

		verify(dummyFileMaker).makeClockFile(day);
	}

	// if a clock exists, and the day isn't over, the image may need redrawing
	@Test
	void CreatesNewClock_AndAssignsIt_IfClockWasMadeToday_FLAKY()
	{
		LocalDateTime now = LocalDateTime.now();
		Period todaysPeriod = new Period(State.WORKING, now, now);

		List<Day> days = ClockManager.createDays(List.of(todaysPeriod));
		Day day = days.get(0);

		ProductivityClock todaysClock = new ProductivityClock(day);
		List<ProductivityClock> clocks = List.of(todaysClock);
		//

		clockManager.assignClocksToDays(clocks, days);

		assertEquals(todaysClock, day.clock());
		verify(dummyFileMaker).makeClockFile(day);
	}
}
