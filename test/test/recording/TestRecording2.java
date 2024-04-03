package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import display.Display;
import recording.*;
import timer.Timer;
import timer.counter.Counter;

public class TestRecording2
{

	@Mock
	Display dummyDisplay = mock(Display.class);
	@Mock
	Counter dummyCounter = mock(Counter.class);
	@Mock
	History dummyHistory = mock(History.class);

	@InjectMocks
	Timer timer;
	
	History2 jsonHistory; 

	@BeforeEach
	void setup()
	{
		jsonHistory = new JsonHistory();
		timer = new Timer(dummyDisplay, dummyCounter, dummyHistory,
		        LocalDateTime.of(2024, 4, 2, 0, 0), jsonHistory);
	}

	@Test
	void HistoryShouldBeEmpty()
	{
		assertEquals(Collections.emptyList(), jsonHistory.getSessions());
	}
}
