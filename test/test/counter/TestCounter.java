package test.counter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static test.counter.FastCounter.DURATION_MILLISECONDS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import timer.counter.ScheduledCounter;

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

//
//	@Test
//	void shouldStopCounter()
//	{
//		counter.countUp();
//		counter.stop();
//		assertFalse(counter.isRunning());
//	}
//
//	@Test
//	void shouldCountDown()
//	{
//		counter.count(1);
//		wait(1.5);
//
//		assertFalse(counter.isRunning());
//		verify(mockTimer, times(1)).showTime();
//	}
//
//	@Test
//	void shouldCountOnlyOnce_IfStartedTwice()
//	{
//		counter.countUp();
//		counter.countUp();
//		wait(1.5);
//
//		verify(mockTimer, times(1)).showTime();
//	}
//
//	@Test
//	void shouldStopCountingDownAfterTimePasses()
//	{
//		counter.count(1);
//		wait(1.5);
//
//		assertFalse(counter.isRunning());
//		verify(mockTimer, times(1)).showTime();
//	}
//
//	@Test
//	void shouldNotThrowExceptionIfRestarted()
//	{
//		counter.countUp();
//		counter.stop();
//	}
//
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
