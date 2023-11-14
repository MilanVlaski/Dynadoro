package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import timer.Clock;
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

	//
	private static final LocalDateTime TIME = LocalDateTime.of(2023, 11, 13, 16, 37);

	@BeforeEach
	void setup()
	{
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void timeShouldBeZero_IfNotStarted()
	{ assertEquals(0, timer.displayedTime(TIME)); }

	@Test
	void shouldMeasureElapsedTime()
	{
		Moment moment = new Moment(TIME);
		
		timer.begin(moment.current());
		assertEquals(1, timer.displayedTime(moment.afterSeconds(1)));
		assertEquals(2, timer.displayedTime(moment.afterSeconds(1)));
	}

}
