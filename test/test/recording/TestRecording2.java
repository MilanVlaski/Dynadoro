package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

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

	History2 fakeHistory;

	@BeforeEach
	void setup()
	{
		fakeHistory = new FakeHistory();
		timer = new Timer(dummyDisplay, dummyCounter, dummyHistory,
		        LocalDateTime.of(2024, 4, 2, 0, 0), fakeHistory);
	}

	@Test
	void HistoryShouldBeEmpty()
	{
		assertEquals(0, fakeHistory.getSessions().size());
	}

//	@Test
//	void HistoryRecordsOneWorkSession()
//	{
//		var time = LocalDateTime.of(2024, 4, 2, 0, 0);
//		LocalDateTime sevenSecLater = time.plusSeconds(7);
//		timer.begin(time);
//		timer.reset(sevenSecLater);
//
//		assertEquals(new Period(State.WORKING, time, sevenSecLater),
//		        fakeHistory.getSessions().getFirst());
//	}
}
