package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import record.Day;
import record.StateData;
import record.StateData.State;
import test.TestTimer.Moment;

class TestShowingDay
{
	@Test 
	void shouldVisualize35MinutesOfWorking()
	{
		Moment moment = new Moment(TestRecord.SEVENTH_NOVEMBER_15_40);

//		StateData work = new StateData(State.WORKING, moment.current(),
//		        moment.after(35 * 60));

//		Day day = new Day(work);
	}

}
