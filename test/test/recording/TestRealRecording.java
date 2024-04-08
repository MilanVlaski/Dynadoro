package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import recording.*;

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
	void RecordsSession()
	{
		history = new SessionHistory("testSessions.json");
		LocalDate date = LocalDate.of(2024, 7, 4);
		LocalTime time = LocalTime.of(20, 40);
		Period period = new Period(State.WORKING, date, time, time.plusMinutes(20));

		history.capture(period);
		assertEquals(new Day(List.of(period)), history.getDays().get(0));
	}

}
