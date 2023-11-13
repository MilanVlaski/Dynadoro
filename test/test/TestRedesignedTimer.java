package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import timer.Clock;
import timer.Moment;
import timer.Timer;
import timer.counter.Counter;

public class TestRedesignedTimer
{

	@Mock
	Clock dummyClock;
	@Mock
	Display dummyDisplay;
	@Mock
	Counter counter;
	
	@InjectMocks
	Timer timer;
	
	@BeforeEach
	void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

	
	@Test
	void timeShouldBeZero_IfNotStarted()
	{
		LocalDateTime time = LocalDateTime.of(2023, 11, 13, 16, 37);
		Moment current = new Moment(time);


		assertEquals(0, timer.displayedTime(current));
	}

}
