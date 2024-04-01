package test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import display.Display;
import timer.Timer;
import timer.counter.Counter;
import timer.state.TimerState.IllegalOperationException;

public class TestTimerExceptions
{
	@Mock
	Display dummyDisplay;
	@Mock
	Counter dummyCounter;

	@InjectMocks
	Timer timer;

	private static final LocalDateTime ANY = TestTimer.ANY_DATETIME;

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

}
