package record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UsageHistory implements History
{

	private static final String APP_NAME = "Dynadoro";
	private static final String userHome = System.getProperty("user.home");
	private static Path path = Paths.get(userHome, APP_NAME, "sessions.txt");

	@Override
	public String read()
	{
		if (Files.notExists(path))
			return "";
		else
			try
			{
				return Files.readString(path);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		return "";
	}

	@Override
	public void write(String text)
	{
		try (BufferedWriter writer = Files.newBufferedWriter(path,
		        StandardOpenOption.APPEND))
		{
			if (Files.notExists(path))
			{
				Files.createDirectories(path.getParent());
				Files.createFile(path);
			}

			writer.write(text);
			writer.newLine();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		// Makes a file and prints "true" if it exists
		UsageHistory history = new UsageHistory();
		history.read();
		System.out.println(Files.exists(path));
	}
}
