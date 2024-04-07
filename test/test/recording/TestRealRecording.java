package test.recording;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

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
		LocalDateTime time = LocalDateTime.of(2024, 7, 4, 20, 40);
		Period period = new Period(State.WORKING, time, time.plusMinutes(20));

		history.capture(period);
		assertEquals(period, history.getSessions().get(0));
	}
	
}
