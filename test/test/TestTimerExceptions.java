package test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;
import timer.state.TimerState.IllegalOperationException;

public class TestTimerExceptions
{
	@Mock
	Clock dummyClock;
	@Mock
	Display dummyDisplay;
	@Mock
	Counter dummyCounter;

	@InjectMocks
	Timer timer;

	private static final LocalDateTime ANY = TestRedesignedTimer.TIME;

	@BeforeEach
	void setup()
	{ MockitoAnnotations.openMocks(this); }

	@Test
	void shouldThrow_IfTriesToStartTwice()
	{
		timer.begin(ANY);
		assertThrows(IllegalOperationException.class,
		        () -> timer.begin(ANY));
	}

	@Test
	void shouldThrow_IfPausingNothing()
	{ assertThrows(IllegalOperationException.class,
	        () -> timer.pause(ANY)); }

	@Test
	void shouldThrow_IfTakesRestWithoutWorking()
	{ assertThrows(IllegalOperationException.class, () -> timer.rest(ANY)); }

	@Test
	void shouldThrow_IfTriesToTakeRestWhileResting()
	{
		timer.begin(ANY);
		timer.rest(ANY);
		assertThrows(IllegalOperationException.class, () -> timer.rest(ANY));
	}

	@Test
	void shouldThrow_IfTriesToResumeNothing()
	{ assertThrows(IllegalOperationException.class,
	        () -> timer.resume(ANY)); }

// I don't care enough about this one
//	@Test
//	void shouldThrow_IfRests_WhilePausingRest()
//	{
//		timer.begin(ANY);
//		timer.rest(ANY);
//		timer.pause(ANY);
//
//		assertThrows(IllegalOperationException.class, () -> timer.rest(ANY));
//	}
}
