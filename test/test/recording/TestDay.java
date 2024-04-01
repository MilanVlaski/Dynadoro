package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;

import recording.*;

public class TestDay
{

	@Test
	void TimeWorkedIsFiveMinutes_IfDayHasFiveMinuteWorkPeriod()
	{
		Day day = new Day(
		        List.of(new Period(State.WORKING, LocalDateTime.of(2023, 12, 1, 14, 0),
		                LocalDateTime.of(2023, 12, 1, 14, 5))));

		assertEquals(Duration.of(5, ChronoUnit.MINUTES), day.timeWorked());
	}

	@Test
	void TimeRestedIsSevenMinutes_IfDayHasFiveMinuteRestPeriod()
	{
		Day day = new Day(
		        List.of(new Period(State.RESTING, LocalDateTime.of(2023, 12, 1, 14, 0),
		                LocalDateTime.of(2023, 12, 1, 14, 5))));

		assertEquals(Duration.of(5, ChronoUnit.MINUTES), day.timeRested());
	}
}
