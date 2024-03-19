package test.record;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;

import org.junit.jupiter.api.*;

import record.UsageHistory;

public class TestMakingFile
{

	static Path directory = Path.of("C:\\Users\\milan\\Dynadoro\\");
	static String fileName = "test";
	static Path path = directory.resolve(fileName + ".txt");

	@BeforeEach
	void deleteFileBefore()
	{ deleteFile(path); }

	@Test
	void UsageHistoryWritesToUserHome()
	{
		var history = new UsageHistory(fileName);
		assertFalse(Files.exists(path));
		
		history.write("something");
		
		assertTrue(history.read().contains("something"));
	}

	@AfterEach
	void deleteFileAfter()
	{ deleteFile(path); }

	void deleteFile(Path path)
	{
		try
		{
			Files.deleteIfExists(path);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
