package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import record.Day;
import record.StateData;
import test.helpers.Moment;

class TestShowingDay
{
	@Test
	void shouldVisualize35MinutesOfWorking()
	{
		Moment moment = new Moment(LocalDateTime.of(2023, 11, 17, 0, 0));

		StateData work = new StateData("Working", moment.current(),
		        moment.afterMinutes(25));

		Day day = new Day(work);

		assertEquals("17-11-2023, Friday, WORK 0-25", day.toString());
	}

}
