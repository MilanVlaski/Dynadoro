package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static recording.State.RESTING;
import static recording.State.WORKING;

import java.time.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import display.Display;
import recording.*;
import test.helpers.FakeHistory;
import timer.Timer;
import timer.counter.Counter;
import timer.state.TimerState.SessionTooLong;

public class TestRecordingSessions
{

	@Mock
	Display dummyDisplay = mock(Display.class);
	@Mock
	Counter dummyCounter = mock(Counter.class);
	@Mock
	History dummyHistory = mock(History.class);

	@InjectMocks
	Timer timer;

	History2 history;

	static final LocalDateTime dateTime = LocalDateTime.of(2024, 4, 2, 0, 0);

	@BeforeEach
	void setup()
	{
		// It's fake, because it doesn't access the file system.
		history = new FakeHistory();
		timer = new Timer(dummyDisplay, dummyCounter, dummyHistory,
		        LocalDateTime.of(2024, 4, 2, 0, 0), history);
	}

	@Test
	void HistoryShouldBeEmpty()
	{
		assertEquals(0, history.getSessions().size());
	}

	@Test
	void HistoryRecordsOneWorkSession()
	{
		LocalDateTime sevenMinLater = dateTime.plusMinutes(7);

		timer.begin(dateTime);
		timer.reset(sevenMinLater);

		assertEquals(new Session(WORKING, dateTime, sevenMinLater),
		             history.getSessions().getFirst());
	}

	@Test
	void HistoryRecordsOneWork_AndOneRestSession()
	{
		LocalDateTime twenyFiveLater = dateTime.plusMinutes(25);
		LocalDateTime twentySixLater = twenyFiveLater.plusMinutes(1);

		timer.begin(dateTime);
		timer.rest(twenyFiveLater);
		timer.reset(twentySixLater);

		assertEquals(List.of(new Session(WORKING, dateTime, twenyFiveLater),
		                     new Session(RESTING, twenyFiveLater, twentySixLater)),
		             history.getSessions());
	}

	@Test
	void RecordsWokSessions_ButNotPauseSession()
	{
		var threeLater = dateTime.plusMinutes(3);
		var sevenLater = dateTime.plusMinutes(4);
		var twelveLater = dateTime.plusMinutes(5);

		timer.begin(dateTime);
		timer.pause(threeLater);
		timer.begin(sevenLater);
		timer.reset(twelveLater);

		assertEquals(List.of(new Session(WORKING, dateTime, threeLater),
		                     new Session(WORKING, sevenLater, twelveLater)),
		             history.getSessions());
	}

	@Test
	void DoesNotRecordsSessionsThatLastLessThanMinute()
	{
		timer.begin(dateTime.plusSeconds(1));
		timer.pause(dateTime.plusSeconds(59));

		assertEquals(0, history.getSessions().size());
	}

	@Test
	void SplitsSessionAtMidnight_IfSessionGoesOverMidnight()
	{
		var date = LocalDate.of(2023, 5, 1);
		var secBeforeMidnight = LocalDateTime.of(date, LocalTime.of(23, 59, 59));
		LocalDateTime midnight = LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0));

		var tenToMidnight = LocalDateTime.of(date, LocalTime.of(23, 50));
		var fiveMinPastMidnight = tenToMidnight.plusMinutes(15);

		timer.begin(tenToMidnight);
		timer.reset(fiveMinPastMidnight);

		assertEquals(List.of(new Session(WORKING, tenToMidnight, secBeforeMidnight),
		                     new Session(WORKING, midnight, fiveMinPastMidnight)),
		             history.getSessions());
	}

	@Test
	void ThrowsIfSessionLongerThanTwentyFourHours()
	{
		timer.begin(dateTime);
		assertThrows(SessionTooLong.class, () -> timer.reset(dateTime.plusDays(2)));
	}
}
