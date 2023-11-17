package test.counter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static test.helpers.FastCounter.DURATION_MILLISECONDS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import test.helpers.FastCounter;

class TestCounter
{

	@Mock
	Display mockDisplay;

	@InjectMocks
	FastCounter counter;

	@BeforeEach
	void setup()
	{ MockitoAnnotations.openMocks(this); }

	@Test
	void shouldCountUp()
	{
		counter.countUp();
		wait(1.5);

		assertTrue(counter.isRunning());
		verify(mockDisplay, times(1)).tickTime();
	}


	@Test
	void shouldStopCounter()
	{
		counter.countUp();
		counter.stop();
		assertFalse(counter.isRunning());
	}

	@Test
	void shouldCountDown()
	{
		counter.count(1);
		wait(1.5);

		assertFalse(counter.isRunning());
		verify(mockDisplay, times(1)).tickTime();
	}

	@Test
	void shouldCountOnlyOnce_IfStartedTwice()
	{
		counter.countUp();
		counter.countUp();
		wait(1.5);

		verify(mockDisplay, times(1)).tickTime();
	}

	@Test
	void shouldStopCountingDownAfterTimePasses()
	{
		counter.count(1);
		wait(1.5);

		assertFalse(counter.isRunning());
		verify(mockDisplay, times(1)).tickTime();
	}

	@Test
	void shouldNotThrowExceptionIfRestarted()
	{
		counter.countUp();
		counter.stop();
	}

	private void wait(double seconds)
	{
		try
		{
			Thread.sleep((long) (seconds * DURATION_MILLISECONDS));

		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
