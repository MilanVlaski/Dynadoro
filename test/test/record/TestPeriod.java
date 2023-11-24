
package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import record.Period;
import record.State;
import test.helpers.Moment;

public class TestPeriod
{

	Moment moment;
	Period period;

	@BeforeEach
	void setup()
	{
		moment = new Moment(LocalDateTime.of(2023, 11, 7, 15, 40));
		period = new Period(State.WORKING, moment.current(), true);
	}

	@Test
	void stateDataShouldWriteProperly()
	{
		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown",
		        period.toString());
	}

	@Test
	void stateDataShouldWriteProperly_AfterItsFinished()
	{
		period.finish(moment.afterMinutes(5));
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:45",
		        period.toString());
	}

	@Test
	void shouldReturnDuration()
	{
		period.finish(moment.afterMinutes(5));

		assertEquals(Duration.between(moment.current(), moment.afterMinutes(5)),
		        period.duration());
	}

	@Test
	void shouldReturnEmptyDuration_IfNotFinished()
	{ assertEquals(Duration.ZERO, period.duration()); }
}
