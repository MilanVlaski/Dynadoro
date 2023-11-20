package test.drawing;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import record.Day;

class TestDateToArcConversion
{

	@Test
	void shouldConvertMidnight_ToNinetyDegrees()
	{ 
		LocalTime midnight = LocalTime.of(0, 0);
		assertEquals(90, Day.timeToDegrees(midnight));
	}
	
	@Test
	void shouldConvertOneAM_ToSixtyDegrees()
	{ 
		LocalTime midnight = LocalTime.of(1, 0);
		assertEquals(60, Day.timeToDegrees(midnight));
	}
	
	@Test
	void shouldConvertFiveAM_ToMinusSixtyDegrees()
	{ 
		LocalTime midnight = LocalTime.of(5, 0);
		assertEquals(-60, Day.timeToDegrees(midnight));
	}
	
	@Test
	void shouldConvertFivePM_ToMinusSixtyDegrees()
	{ 
		LocalTime midnight = LocalTime.of(17, 0);
		assertEquals(-60, Day.timeToDegrees(midnight));
	}
	
//	@Test
//	void shouldConvertOneThirtyAM_()
//	{ 
//		LocalTime midnight = LocalTime.of(1, 30);
//		assertEquals(-60, Day.timeToDegrees(midnight));
//	}

}
