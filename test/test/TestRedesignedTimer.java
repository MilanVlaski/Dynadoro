package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Duration;
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
	Counter dummyCounter;

	@InjectMocks
	Timer timer;

	//
	private static final LocalDateTime TIME = LocalDateTime.of(2023, 11, 13, 16, 37);
	private Moment moment;

	@BeforeEach
	void setup()

	{
		MockitoAnnotations.openMocks(this);
		moment = new Moment(TIME);
	}

	@Test
	void timeShouldBeZero_IfNotStarted()
	{ assertEquals(0, timer.seconds(TIME)); }

	@Test
	void timeShouldMoveForward_AfterOneSecond()
	{
		timer.begin(moment.current());
		
		assertEquals(0, timer.seconds(moment.current()));
		assertEquals(1, timer.seconds(moment.afterSeconds(1)));
	}

	@Test
	void restShouldTakeFiveTimesShorterThanWork()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterSeconds(25));
		
		assertEquals(5, timer.seconds(moment.current()));
	}
}
