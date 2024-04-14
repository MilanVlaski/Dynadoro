package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import recording.*;

public class TestRetrievingDays
{
	History2 history;

	@TempDir
	Path tempDir;

	@BeforeEach
	void setup()
	{
		history = new SessionHistory(tempDir.resolve("testSessions.json"));
	}

	@Test
	void GivesNoDays_IfNothingWasRecorded()
	{ assertEquals(0, history.getDays().size()); }

	@Nested
	class RecordsSession
	{

		LocalDate date = LocalDate.of(2024, 7, 4);
		LocalTime time = LocalTime.of(20, 40);
		Session session = new Session(State.WORKING, date, time, time.plusMinutes(20));

		@Test
		void IfNothingWasRecordedBefore()
		{
			history.capture(session);

			List<Day> days = history.getDays();
			assertEquals(1, days.size());
			assertEquals(1, days.get(0).numberOfPeriods());
		}

		@Test
		void InsideExistingDay()
		{
			history.capture(session);
			history.capture(session);

			List<Day> days = history.getDays();
			assertEquals(1, days.size());
			assertEquals(2, days.get(0).numberOfPeriods());
		}

		@Test
		void InTwoDifferentDays()
		{
			history.capture(session);
			history.capture(new Session(State.WORKING, date.plusDays(1), time,
			        time.plusMinutes(10)));

			List<Day> days = history.getDays();
			assertEquals(1, days.size());
			assertEquals(1, days.get(0).numberOfPeriods());
			assertEquals(1, days.get(1).numberOfPeriods());
		}
	}


}
