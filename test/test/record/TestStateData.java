package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import record.StateData;
import test.helpers.Moment;

public class TestStateData
{

	Moment moment;
	StateData stateData;

	@BeforeEach
	void setup()
	{
		moment = new Moment(LocalDateTime.of(2023, 11, 7, 15, 40));
		stateData = new StateData("Working", moment.current(), true);
	}

	@Test
	void stateDataShouldWriteProperly()
	{
		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown",
		        stateData.toString());
	}

	@Test
	void stateDataShouldWriteProperly_AfterItsFinished()
	{
		stateData.finish(moment.afterMinutes(5));
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:45",
		        stateData.toString());
	}

	@Test
	void shouldReturnDuration()
	{
		StateData stateData = new StateData("Working", moment.current(), true);
		stateData.finish(moment.afterMinutes(5));

		assertEquals(Duration.between(moment.current(), moment.afterMinutes(5)),
		        stateData.duration());
	}

	@Test
	void shouldReturnEmptyDuration_IfNotFinished()
	{
		assertEquals(Duration.ZERO, stateData.duration());
	}
}
