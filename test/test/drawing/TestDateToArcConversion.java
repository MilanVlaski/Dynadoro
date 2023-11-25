package test.drawing;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import record.ProductivityClock;

class TestDateToArcConversion
{

	@Test
	void ConvertMidnight_ToNinety()
	{
		LocalTime midnight = LocalTime.of(0, 0);
		assertEquals(90, ProductivityClock.timeToDegrees(midnight));
	}

	@Test
	void ConvertOneAM_ToSixty()
	{
		LocalTime oneAM = LocalTime.of(1, 0);
		assertEquals(60, ProductivityClock.timeToDegrees(oneAM));
	}

	@Test
	void ConvertFiveAM_ToMinusSixty()
	{
		LocalTime fiveAM = LocalTime.of(5, 0);
		assertEquals(-60, ProductivityClock.timeToDegrees(fiveAM));
	}

	@Test
	void ConvertFivePM_ToMinusSixty()
	{
		LocalTime fivePM = LocalTime.of(17, 0);
		assertEquals(-60, ProductivityClock.timeToDegrees(fivePM));
	}

	@Test
	void ConvertOneThirtyAM_ToFortyFive()
	{
		LocalTime oneThirty = LocalTime.of(1, 30);
		assertEquals(45, ProductivityClock.timeToDegrees(oneThirty));
	}

	@Test
	void ConvertTenMinutes_ToEightyFive()
	{
		LocalTime oneThirty = LocalTime.of(0, 10);
		assertEquals(85, ProductivityClock.timeToDegrees(oneThirty));
	}

	@Test
	void ConvertFiveMinutes_ToEightySevenPtFive()
	{
		LocalTime oneThirty = LocalTime.of(0, 5);
		assertEquals(87.5, ProductivityClock.timeToDegrees(oneThirty));
	}

	@Test
	void ConvertTwoMinutes_ToEightyNine()
	{
		LocalTime oneThirty = LocalTime.of(0, 2);
		assertEquals(89, ProductivityClock.timeToDegrees(oneThirty));
	}

	@Test
	void ConvertOneMinute_ToEightyNinePtFive()
	{
		LocalTime oneThirty = LocalTime.of(0, 1);
		assertEquals(89.5, ProductivityClock.timeToDegrees(oneThirty));
	}

}
