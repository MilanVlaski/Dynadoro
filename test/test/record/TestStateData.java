package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import record.StateData;
import test.helpers.Moment;

public class TestStateData
{
	
	Moment moment;

	@BeforeEach
	void setup() {
		moment = new Moment(LocalDateTime.of(2023, 11, 7, 15, 40));
	}
	
	@Test
	void stateDataShouldWriteProperly()
	{
		StateData stateData = new StateData("Working", moment.current(), true);
		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown",
		        stateData.toString());
	}

	@Test
	void stateDataShouldWriteProperly_AfterItsFinished()
	{
		StateData stateData = new StateData("Working", moment.current(), true);
		stateData.finish(moment.afterMinutes(5));
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:45",
		        stateData.toString());
	}
}
