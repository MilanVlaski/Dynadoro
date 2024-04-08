package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import recording.History2;
import recording.SessionHistory;

public class TestRealRecording
{
	History2 history;

//	@BeforeEach
//	void deleteFileBefore()
//	{ deleteFile(path); }
//	
//	@AfterEach
//	void deleteFileAfter()
//	{ deleteFile(path); }
//
//	void deleteFile(Path path)
//	{
//		try
//		{
//			Files.deleteIfExists(path);
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}

	@Test
	void GivesEmptyList_IfNoFilePresent()
	{
		history = new SessionHistory("testSessions.json");
		assertEquals(0, history.getDays().size());
	}

//	@Test
//	void RecordsSession()
//	{
//		history = new SessionHistory("testSessions.json");
//		LocalDate date = LocalDate.of(2024, 7, 4);
//		LocalTime time = LocalTime.of(20, 40);
//		Period period = new Period(State.WORKING, date, time, time.plusMinutes(20));
//
//		history.capture(period);
//		assertEquals(new Day(List.of(period)), history.getDays().get(0));
//	}

}
