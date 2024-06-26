package recording;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestDay
{

	@Test
	void TimeWorkedIsFiveMinutes_IfDayHasFiveMinuteWorkPeriod()
	{
		Day day = new Day(
		        List.of(new Session(State.WORKING, LocalDate.of(2023, 12, 1),
		        		LocalTime.of(14, 0),
		                LocalTime.of(14, 5))));

		assertEquals(Duration.of(5, ChronoUnit.MINUTES), day.timeWorked());
	}

	@Test
	void TimeRestedIsSevenMinutes_IfDayHasFiveMinuteRestPeriod()
	{
		Day day = new Day(
				 List.of(new Session(State.RESTING, LocalDate.of(2023, 12, 1),
			        		LocalTime.of(14, 0),
			                LocalTime.of(14, 5))));

		assertEquals(Duration.of(5, ChronoUnit.MINUTES), day.timeRested());
	}
}
