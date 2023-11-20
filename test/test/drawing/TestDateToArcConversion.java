package test.drawing;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import record.Day;

class TestDateToArcConversion
{

	@Test
	void shouldConvertMidnight_ToNinety()
	{
		LocalTime midnight = LocalTime.of(0, 0);
		assertEquals(90, Day.timeToDegrees(midnight));
	}

	@Test
	void shouldConvertOneAM_ToSixty()
	{
		LocalTime oneAM = LocalTime.of(1, 0);
		assertEquals(60, Day.timeToDegrees(oneAM));
	}

	@Test
	void shouldConvertFiveAM_ToMinusSixty()
	{
		LocalTime fiveAM = LocalTime.of(5, 0);
		assertEquals(-60, Day.timeToDegrees(fiveAM));
	}

	@Test
	void shouldConvertFivePM_ToMinusSixty()
	{
		LocalTime fivePM = LocalTime.of(17, 0);
		assertEquals(-60, Day.timeToDegrees(fivePM));
	}

	@Test
	void shouldConvertOneThirtyAM_ToFortyFive()
	{
		LocalTime oneThirty = LocalTime.of(1, 30);
		assertEquals(45, Day.timeToDegrees(oneThirty));
	}

	@Test
	void shouldConvertTenMinutes_ToEightyFive()
	{
		LocalTime oneThirty = LocalTime.of(0, 10);
		assertEquals(85, Day.timeToDegrees(oneThirty));
	}

	@Test
	void shouldConvertFiveMinutes_ToEightySeven()
	{
		LocalTime oneThirty = LocalTime.of(0, 5);
		assertEquals(88, Day.timeToDegrees(oneThirty));
	}

	@Test
	void shouldConvertTwoMinutes_ToOneEightyNine()
	{
		LocalTime oneThirty = LocalTime.of(0, 2);
		assertEquals(89, Day.timeToDegrees(oneThirty));
	}

	@Test
	void shouldConvertOneMinute_ToNinety()
	{
		LocalTime oneThirty = LocalTime.of(0, 1);
		assertEquals(90, Day.timeToDegrees(oneThirty));
	}

}
