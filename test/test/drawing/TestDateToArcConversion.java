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

}
