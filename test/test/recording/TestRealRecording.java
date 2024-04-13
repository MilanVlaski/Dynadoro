package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import recording.*;

public class TestRealRecording
{
	History2 history;

	@TempDir
	Path tempDir;

	@BeforeEach
	void deleteFileBefore()
	{
		history = new SessionHistory(tempDir.resolve("testSessions.json"));
	}

	@Test
	void GivesEmptyList_IfNoFilePresent()
	{ assertEquals(0, history.getDays().size()); }

	@Test
	void RecordsSession()
	{
		LocalDate date = LocalDate.of(2024, 7, 4);
		LocalTime time = LocalTime.of(20, 40);
		Period period = new Period(State.WORKING, date, time, time.plusMinutes(20));

		history.capture(period);
		assertEquals(new Day(List.of(period)), history.getDays().get(0));
	}

}
