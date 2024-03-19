package test.record;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.*;

import org.junit.jupiter.api.*;

import record.UsageHistory;

public class TestMakingFile
{

	static Path directory = Path.of("C:\\Users\\milan\\Dynadoro\\");
	static String fileName = "test";
	static Path file = directory.resolve(fileName + ".txt");

	@BeforeEach
	void deleteFileBefore()
	{ deleteFile(file); }

	@Test
	void UsageHistoryWritesToUserHome()
	{
		var history = new UsageHistory(fileName);
		history.write("something");
		assertTrue(Files.exists(file));
	}

	@AfterEach
	void deleteFileAfter()
	{ deleteFile(file); }

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
